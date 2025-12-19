/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.session;

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

/**
 * Servlet for testing concurrent access to HTTP sessions.
 *
 * First request stores an AtomicInteger attribute in the HttpSession.
 * Subsequent requests read the session attribute, call incrementAndGet() and return the result.
 *
 * This servlet is designed to be used with a test client using a pool of threads
 * that invokes a barrage of requests with simulated failover and failback.
 * The test should ensure that each response contains a unique value.
 *
 * @author Radoslav Husar
 */
@WebServlet(name = "ConcurrentHttpSessionServlet", urlPatterns = { "/concurrent" })
public class ConcurrentHttpSessionServlet extends HttpServlet {

    protected static final Logger log = Logger.getLogger(ConcurrentHttpSessionServlet.class.getName());
    public static final String KEY = ConcurrentHttpSessionServlet.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        AtomicInteger counter;

        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());
            counter = new AtomicInteger(0);
            session.setAttribute(KEY, counter);
        } else {
            counter = (AtomicInteger) session.getAttribute(KEY);
            if (counter == null) {
                log.log(Level.SEVERE, "Session is not new but counter is null - session corrupted: {0}", session.getId());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Session corrupted: counter is null");
                return;
            }
        }

        // Increment and get the value
        int value = counter.incrementAndGet();

        resp.setContentType("text/plain");
        resp.getWriter().print(value);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for testing concurrent access to HTTP sessions using AtomicInteger.";
    }
}
