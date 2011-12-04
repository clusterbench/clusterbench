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

@WebServlet(name = "GranularSessionServlet", urlPatterns = {"/granular"})
public class GranularSessionServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(GranularSessionServlet.class.getName());
    public static final String KEY_SERIAL = GranularSessionServlet.class.getName() + "Serial";
    public static final String KEY_CARGO = GranularSessionServlet.class.getName() + "Cargo";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());
            // Reuse serial bean logic to generate the data.
            SerialBean tempBean = new SerialBean();
            session.setAttribute(KEY_SERIAL, tempBean.getSerial());
            session.setAttribute(KEY_CARGO, tempBean.getCargo());
        }

        Integer serial = (Integer) session.getAttribute(KEY_SERIAL);
        // Do nothing with cargo?
        byte[] cargo = (byte[]) session.getAttribute(KEY_CARGO);

        resp.setContentType("text/plain");

        // Now store it in an attribute
        session.setAttribute(KEY_SERIAL, serial + 1);

        resp.getWriter().print(serial);
    }

    @Override
    public String getServletInfo() {
        return "Servlet using HTTP Session attributes to store serial and cargo separately.";
    }
}
