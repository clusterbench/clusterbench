package org.jboss.qa.clusterbench.cdi;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CdiServlet", urlPatterns = {"/cdi"})
public class CdiServlet extends HttpServlet {

    @Inject
    private SessionScopedCdiSerialBean bean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        int id = bean.getSerial();
        bean.setSerial(id + 1);

        resp.getWriter().print(id);
    }

    @Override
    public String getServletInfo() {
        return "Servlet using CDI bean to store serial.";
    }
}
