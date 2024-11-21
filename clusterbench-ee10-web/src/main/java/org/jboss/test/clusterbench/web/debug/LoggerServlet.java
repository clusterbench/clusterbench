/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.debug;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.test.clusterbench.common.ClusterBenchConstants;

/**
 * Logging servlet that logs provided message in 'msg' parameter.
 * Can also be called without parameters as a 'ping' mechanism which
 * Optionally, log level can be provided in the 'level' parameter.
 * The default level is INFO.
 *
 * @author Tommaso Borgato
 * @author Radoslav Husar
 */
@WebServlet(name = "LoggerServlet", urlPatterns = {"/log"})
public class LoggerServlet extends HttpServlet {

    // Servlet parameters and their default values
    public static final String MESSAGE_PARAM = "msg";
    public static final String DEFAULT_MESSAGE = "ping";
    public static final String LEVEL_PARAM = "level";
    public static final Level DEFAULT_LEVEL = Level.INFO;

    private static final Logger LOG = Logger.getLogger(LoggerServlet.class.getCanonicalName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestLevel = request.getParameter(LEVEL_PARAM);
        String message = request.getParameter(MESSAGE_PARAM);

        Level level = null;
        if (requestLevel != null) {
            try {
                level = Level.parse(requestLevel);
            } catch (Exception ignored) {
                // Ignore silently and use default.
            }
        }

        LOG.log(level == null ? DEFAULT_LEVEL : level, message == null ? DEFAULT_MESSAGE : message);

        response.getWriter().print(ClusterBenchConstants.SUCCESS);
    }
}
