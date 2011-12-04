package org.jboss.test.clusterbench.web.cdi;

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

        int serial = bean.getSerial();
        bean.setSerial(serial + 1);

        resp.getWriter().print(serial);
    }

    @Override
    public String getServletInfo() {
        return "Servlet using CDI bean to store serial.";
    }
}
