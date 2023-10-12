/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.load;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.test.clusterbench.common.load.AverageSystemLoad;

/**
 * AverageSystemLoadServlet is used for stressing the server's CPU.
 * <p>
 * Usage:
 * <p>
 * You may GET e.g. this URL: <a href="http://localhost:8080/clusterbench/averagesystemload?milliseconds=20000&threads=4">http://localhost:8080/clusterbench/averagesystemload?milliseconds=20000&amp;threads=4</a>
 * By doing so, there will be 4 threads with evil active-loops created.
 * These threads will be running for 20000 milliseconds.
 * <p>
 * After the aforementioned time, you shall receive a response saying something like:
 * <p>
 * DONE, I was stressing CPU with 4 evil threads for 20000 milliseconds (including warm-up).
 * <p>
 * NOTE: Do not forget to set some reasonable time-out on your client...
 * That's it. No more functionality.
 *
 * @author Michal Babacek
 */
@WebServlet(name = "AverageSystemLoadServlet", urlPatterns = {"/averagesystemload"})
public class AverageSystemLoadServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(AverageSystemLoadServlet.class.getName());
    private final AverageSystemLoad averageSystemLoad = new AverageSystemLoad();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
        int numberOfThreads = Integer.parseInt(request.getParameter("threads"));
        response.setContentType("text/plain");
        String resultLogMessage = averageSystemLoad.spawnLoadThreads(numberOfThreads, milliseconds);
        log.log(Level.INFO, resultLogMessage);
        response.getWriter().print("DONE");
    }

    @Override
    public String getServletInfo() {
        return "By invoking AverageSystemLoadServlet, you stress CPU.";
    }
}
