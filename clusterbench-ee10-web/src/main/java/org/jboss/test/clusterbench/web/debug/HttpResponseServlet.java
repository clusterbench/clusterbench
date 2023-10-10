/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.debug;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Michal Karm Babacek
 * @author Radoslav Husar
 */
@WebServlet(name = "HttpResponseServlet", urlPatterns = { "/http-response", "/http-response/*" })
public class HttpResponseServlet extends HttpServlet {

    public static final String CODE_PARAM = "code";

    private static final Logger log = Logger.getLogger(HttpResponseServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int httpCode = Integer.parseInt(request.getParameter(CODE_PARAM));
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        // Gives it a JSESSIONID
        HttpSession session = request.getSession();
        StringBuilder responseText = new StringBuilder();
        responseText.append("Done. ");
        responseText.append("\n");
        responseText.append("HTTP Code was: ");
        responseText.append(httpCode);
        responseText.append("\n");
        responseText.append("JVM route: ");
        String jbossNodeName = System.getProperty("jboss.node.name");
        responseText.append(jbossNodeName);
        responseText.append("\n");
        responseText.append("Session ID: ");
        responseText.append(session.getId());
        responseText.append("\n");
        responseText.append("Session isNew: ");
        responseText.append(session.isNew());
        responseText.append("\n");
        log.log(Level.INFO, responseText.toString());
        response.getWriter().print(responseText);
        response.setStatus(httpCode);
    }

}
