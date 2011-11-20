package org.jboss.qa.clusterbench.ejb;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.qa.clusterbench.singleton.SingletonSessionBean;

/**
 *
 * @author rhusar
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/ejb"})
public class EjbServlet extends HttpServlet {

    @EJB
    private SingletonSessionBean bean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(bean.getSerial());
    }

    @Override
    public String getServletInfo() {
        return "TBD";
    }
}
