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

@WebServlet(name = "SessionServlet", urlPatterns = {"/granular"})
public class GranularSessionServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(GranularSessionServlet.class.getName());
    public static final String KEY = GranularSessionServlet.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

        if (s.isNew()) {
            log.log(Level.INFO, "New session created: {0}", s.getId());
            s.setAttribute(KEY, new SerialBean());
        }

        SerialBean bean = (SerialBean) s.getAttribute(KEY);

        resp.setContentType("text/plain");

        int id = bean.getSerial();
        bean.setSerial(id + 1);

        // Now store it in the session
        s.setAttribute(KEY, bean);

        resp.getWriter().print(id);
    }

    @Override
    public String getServletInfo() {
        return "Servlet using Session to store serial.";
    }
}
