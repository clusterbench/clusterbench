/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.session;

import java.io.IOException;
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
@WebServlet(name = "HttpSessionServlet", urlPatterns = {"/session", "/session/*"})
public class HttpSessionServlet extends HttpServlet {

    protected static final Logger log = Logger.getLogger(HttpSessionServlet.class.getName());
    public static final String KEY = HttpSessionServlet.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            if (req.getParameter(ClusterBenchConstants.CARGO_SIZE_KB) != null && req.getParameter(ClusterBenchConstants.CARGO_SIZE_KB).matches("[0-9]+")) {
                log.log(Level.INFO, "New session created: {0} with {1} KB cargo", new Object[] {session.getId(), req.getParameter(ClusterBenchConstants.CARGO_SIZE_KB)});
                int cargoSizeKB = Integer.parseInt(req.getParameter(ClusterBenchConstants.CARGO_SIZE_KB));
                session.setAttribute(KEY, new ImmutableSerialBean(cargoSizeKB));
            } else {
                log.log(Level.INFO, "New session created: {0} with {1} KB cargo", new Object[] {session.getId(), SerialBean.DEFAULT_CARGO_SIZE_KB});
                session.setAttribute(KEY, new ImmutableSerialBean());
            }
        } else if (session.getAttribute(KEY) == null) {
            log.log(Level.INFO, "Session is not new, creating SerialBean: {0}", session.getId());
            session.setAttribute(KEY, new ImmutableSerialBean());
        }

        SerialBean bean = (SerialBean) session.getAttribute(KEY);

        resp.setContentType("text/plain");

        // Readonly?
        if (req.getParameter(ClusterBenchConstants.READONLY) != null) {
            resp.getWriter().print(bean.getSerial());
            return;
        }

        int serial = bean.getSerialAndIncrement();

        // Now store bean in the session
        // n.b. in order to support readonly mode that does not trigger mutation of the object we wrap SerialBean in an @Immutable wrapper
        // thus we need to explicitly call setAttribute(..) to trigger mutation after org.jboss.test.clusterbench.common.SerialBean.getSerialAndIncrement
        session.setAttribute(KEY, bean);

        resp.getWriter().print(serial);

        // Invalidate?
        if (req.getParameter(ClusterBenchConstants.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", session.getId());
            session.invalidate();
        }
    }


    @Override
    public String getServletInfo() {
        return "Servlet using HTTP Session to store object with the serial.";
    }
}
