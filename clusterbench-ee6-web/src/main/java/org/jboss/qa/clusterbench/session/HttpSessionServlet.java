package org.jboss.qa.clusterbench.session;

import org.jboss.qa.clusterbench.common.SerialBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HttpSessionServlet", urlPatterns = {"/session"})
public class HttpSessionServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(HttpSessionServlet.class.getName());
    public static final String KEY = HttpSessionServlet.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());
            session.setAttribute(KEY, new SerialBean());
        }

        SerialBean bean = (SerialBean) session.getAttribute(KEY);

        resp.setContentType("text/plain");

        int serial = bean.getSerial();
        bean.setSerial(serial + 1);

        // Now store bean in the session
        session.setAttribute(KEY, bean);

        resp.getWriter().print(serial);
    }

    @Override
    public String getServletInfo() {
        return "Servlet using Session to store object with the serial.";
    }
}
