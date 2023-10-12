/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.ejb;

import java.io.IOException;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.test.clusterbench.ejb.stateful.LocalStatefulSB;

/**
 * @author Radoslav Husar
 */
@WebServlet(name = "LocalEjbServlet", urlPatterns = { "/ejbservlet" })
public class LocalEjbServlet extends HttpServlet {

    @Inject
    private LocalStatefulSB bean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        int serial = bean.getSerialAndIncrement();
        resp.getWriter().print(serial);
    }

    @Override
    public String getServletInfo() {
        return "Servlet invoking Stateful Session Bean to store serial.";
    }
}
