/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.session;

import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet for testing concurrent access to HTTP sessions.
 *
 * Uses AtomicSerialBean which internally uses AtomicInteger for thread-safe concurrent access.
 * The parent class CommonHttpSessionServlet calls getSerialAndIncrement() which is atomic in AtomicSerialBean.
 *
 * This servlet is designed to be used with a test client using a pool of threads
 * that invokes a barrage of requests with simulated failover and failback.
 * The test should ensure that each response contains a unique value.
 *
 * @author Radoslav Husar
 */
@WebServlet(name = "ConcurrentHttpSessionServlet", urlPatterns = { "/concurrent" })
public class ConcurrentHttpSessionServlet extends CommonHttpSessionServlet {

    @Override
    protected Object createSerialBean() {
        return new AtomicSerialBean();
    }

    @Override
    protected Object createSerialBean(int cargokbytes) {
        return new AtomicSerialBean(cargokbytes);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for testing concurrent access to HTTP sessions using AtomicSerialBean.";
    }
}
