package org.jboss.test.clusterbench.common.session;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.test.clusterbench.common.ClusterBenchConstants;
import org.jboss.test.clusterbench.common.SerialBean;

public class CommonGranularHttpSessionServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(CommonGranularHttpSessionServlet.class.getName());
    public static final String KEY_SERIAL = CommonGranularHttpSessionServlet.class.getName() + "Serial";
    public static final String KEY_CARGO = CommonGranularHttpSessionServlet.class.getName() + "Cargo";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            log.log(Level.INFO, "New session created: {0}", session.getId());

            // Reuse serial bean logic to generate the data but don't store the SerialBean instance directly.
            SerialBean tempBean = new SerialBean();
            session.setAttribute(KEY_SERIAL, tempBean.getSerial());
            session.setAttribute(KEY_CARGO, tempBean.getCargo());
        }

        Integer serial = (Integer) session.getAttribute(KEY_SERIAL);
        byte[] cargo = (byte[]) session.getAttribute(KEY_CARGO);

        resp.setContentType("text/plain");

        // Now store the serial in an attribute
        session.setAttribute(KEY_SERIAL, serial + 1);

        // Do nothing with cargo.

        resp.getWriter().print(serial);

        // Invalidate?
        if (req.getParameter(ClusterBenchConstants.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", session.getId());
            session.invalidate();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet using HTTP Session attributes to store serial and cargo separately.";
    }
}
