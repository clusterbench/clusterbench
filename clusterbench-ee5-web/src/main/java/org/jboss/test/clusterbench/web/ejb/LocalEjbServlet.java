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
