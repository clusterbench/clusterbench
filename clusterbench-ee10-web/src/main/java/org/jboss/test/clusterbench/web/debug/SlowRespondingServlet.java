/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.debug;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.test.clusterbench.common.ClusterBenchConstants;

/**
 * Servlet that sleeps for a given number of milliseconds before responding, useful for testing timeouts.
 * <p>
 * Example: <a href="http://localhost:8080/clusterbench/slowresponding?milliseconds=5000">http://localhost:8080/clusterbench/slowresponding?milliseconds=5000</a>
 *
 * @author Radoslav Husar
 */
@WebServlet(name = "SlowRespondingServlet", urlPatterns = {"/slowresponding"})
public class SlowRespondingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int milliseconds = Integer.parseInt(request.getParameter(ClusterBenchConstants.MILLISECONDS));
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        response.setContentType("text/plain");
        response.getWriter().print(ClusterBenchConstants.SUCCESS);
    }

    @Override
    public String getServletInfo() {
        return "By invoking SlowRespondingServlet, the response is delayed by a given number of milliseconds.";
    }
}
