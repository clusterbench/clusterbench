/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.test.clusterbench.web.ejb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.test.clusterbench.ejb.stateful.LocalStatefulSB;

public class LocalEjbServlet extends HttpServlet {

    public static final String ATTR_EJB = "ejb3beanProxy";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();
        LocalStatefulSB bean;

        // Note that EE6 version uses @Inject from Weld/CDI.
        if (session.isNew()) {
            try {
                Context c = new InitialContext();
                bean = (LocalStatefulSB) c.lookup("clusterbench-ee5/LocalStatefulSBImpl/local");
                session.setAttribute(ATTR_EJB, bean);
            } catch (NamingException nex) {
                // Just rethrow
                throw new ServletException("Could not lookup bean.", nex);
            }
        } else {
            bean = (LocalStatefulSB) session.getAttribute(ATTR_EJB);
        }

        out.println(bean.getSerialAndIncrement());
    }

    @Override
    public String getServletInfo() {
        return "Servlet invoking Stateful Session Bean to store serial.";
    }
}
