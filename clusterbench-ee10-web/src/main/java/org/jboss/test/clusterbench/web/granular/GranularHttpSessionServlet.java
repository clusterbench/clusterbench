/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.granular;

import java.io.IOException;
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
 * @author Radoslav Husar
 */
@WebServlet(name = "GranularSessionServlet", urlPatterns = {"/granular"})
public class GranularHttpSessionServlet extends HttpServlet {

    public static final String KEY_SERIAL = GranularHttpSessionServlet.class.getName() + "Serial";
    public static final String KEY_CARGO = GranularHttpSessionServlet.class.getName() + "Cargo";
    private static final Logger log = Logger.getLogger(GranularHttpSessionServlet.class.getName());

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());

            // Reuse serial bean logic to generate the data but don't store the SerialBean instance directly.
            SerialBean tempBean = new SerialBean();

            // Store wrapped serial
            ImmutableWrapper<AtomicInteger> wrappedSerial = new ImmutableWrapper<>(new AtomicInteger(tempBean.getSerial()));
            session.setAttribute(KEY_SERIAL, wrappedSerial);

            // Store wrapper cargo
            ImmutableWrapper<byte[]> wrappedCargo = new ImmutableWrapper<>(tempBean.getCargo());
            session.setAttribute(KEY_CARGO, wrappedCargo);
        }

        ImmutableWrapper<AtomicInteger> serialWrapper = (ImmutableWrapper<AtomicInteger>) session.getAttribute(KEY_SERIAL);

        AtomicInteger serial = serialWrapper.value();

        resp.setContentType("text/plain");

        // Readonly?
        if (req.getParameter(ClusterBenchConstants.READONLY) != null) {
            resp.getWriter().print(serial.get());
            return;
        }

        resp.getWriter().print(serial.getAndIncrement());

        // n.b. we need to call jakarta.servlet.http.HttpSession#setAttribute after mutation explicitly as the AtomicInteger is wrapped in an @Immutable wrapper
        session.setAttribute(KEY_SERIAL, serialWrapper);

        // Invalidate?
        if (req.getParameter(ClusterBenchConstants.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", session.getId());
            session.invalidate();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet using HTTP Session attributes to store serial and cargo separately.";
    }

}
