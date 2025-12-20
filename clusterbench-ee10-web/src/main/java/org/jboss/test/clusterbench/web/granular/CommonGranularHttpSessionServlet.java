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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jboss.test.clusterbench.common.ClusterBenchConstants;
import org.jboss.test.clusterbench.common.SerialBean;

public abstract class CommonGranularHttpSessionServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(CommonGranularHttpSessionServlet.class.getName());
    public static final String KEY_SERIAL = CommonGranularHttpSessionServlet.class.getName() + "Serial";
    public static final String KEY_CARGO = CommonGranularHttpSessionServlet.class.getName() + "Cargo";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());

            // Reuse serial bean logic to generate the data but don't store the SerialBean instance directly.
            SerialBean tempBean = new SerialBean();
            session.setAttribute(KEY_SERIAL, new AtomicInteger(tempBean.getSerial()));
            this.storeCargo(session, tempBean.getCargo());
        }

        AtomicInteger serial = (AtomicInteger) session.getAttribute(KEY_SERIAL);

        resp.setContentType("text/plain");

        // Readonly?
        if (req.getParameter(ClusterBenchConstants.READONLY) != null) {
            resp.getWriter().print(serial.get());
            return;
        }

        resp.getWriter().print(serial.getAndIncrement());

        // n.b. no need to call jakarta.servlet.http.HttpSession#setAttribute as the attribute value is AtomicInteger which is mutable type

        // Invalidate?
        if (req.getParameter(ClusterBenchConstants.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", session.getId());
            session.invalidate();
        }
    }

    public void storeCargo(HttpSession session, byte[] cargo) {
        session.setAttribute(KEY_CARGO, cargo);
    }

    public byte[] loadCargo(HttpSession session) {
        return (byte[]) session.getAttribute(KEY_CARGO);
    }

    @Override
    public String getServletInfo() {
        return "Servlet using HTTP Session attributes to store serial and cargo separately.";
    }
}
