package org.jboss.qa.clusterbench.ejb;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.qa.clusterbench.stateful.LocalStatefulSB;

@WebServlet(name = "LocalEjbServlet", urlPatterns = {"/ejb"})
public class LocalEjbServlet extends HttpServlet {

    @Inject
    // @SessionScoped -> This mistake is not in anyway treated. TODO discuss.
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
