/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.session;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jboss.test.clusterbench.common.ClusterBenchConstants;
import org.jboss.test.clusterbench.common.SerialBean;

/**
 * Servlet that tests the Jakarta Servlet 6.1 {@link HttpSession#getAccessor()} feature.
 * The accessor allows safe session access from non-request threads, which is useful
 * for testing session replication with async operations in a cluster.
 *
 * @author Radoslav Husar
 */
@WebServlet(name = "AccessorServlet", urlPatterns = {"/accessor"})
public class AccessorServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(AccessorServlet.class.getName());
    public static final String KEY = AccessorServlet.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        HttpSession.Accessor accessor = session.getAccessor();
        String sessionId = session.getId();
        boolean isNew = session.isNew();

        String cargoSizeKBParam = req.getParameter(ClusterBenchConstants.CARGO_SIZE_KB);
        int cargoSizeKB = SerialBean.DEFAULT_CARGO_SIZE_KB;
        if (cargoSizeKBParam != null && cargoSizeKBParam.matches("[0-9]+")) {
            cargoSizeKB = Integer.parseInt(cargoSizeKBParam);
        }
        final int finalCargoSizeKB = cargoSizeKB;

        resp.setContentType("text/plain");

        // Use AtomicInteger to capture the serial value from the async operation
        AtomicInteger serialHolder = new AtomicInteger(-1);

        // Perform ALL session operations via accessor from async thread
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> accessor.access(accessedSession -> {
            SerialBean serialBean = (SerialBean) accessedSession.getAttribute(KEY);

            // Initialize if needed
            if (serialBean == null) {
                if (isNew) {
                    log.log(Level.INFO, "New session created: {0} with {1} KB cargo", new Object[] {sessionId, finalCargoSizeKB});
                } else {
                    log.log(Level.INFO, "Session is not new, creating SerialBean: {0}", sessionId);
                }
                serialBean = new SerialBean(finalCargoSizeKB);
            }

            // Readonly?
            if (req.getParameter(ClusterBenchConstants.READONLY) != null) {
                serialHolder.set(serialBean.getSerial());
                return;
            }

            serialHolder.set(serialBean.getSerialAndIncrement());
            accessedSession.setAttribute(KEY, serialBean);
            log.log(Level.FINE, "Async accessor incremented serial to {0}", serialBean.getSerial());
        }));

        // Wait for async completion so that we can access serialHolder and respond
        future.join();

        resp.getWriter().print(serialHolder.get());

        // Invalidate?
        if (req.getParameter(ClusterBenchConstants.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", sessionId);
            session.invalidate();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet using HttpSession.Accessor to test async session access (Jakarta Servlet 6.1).";
    }
}
