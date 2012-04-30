package org.jboss.test.clusterbench.common.debug;

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

/**
 * Let this Servlet just output some debug information provided in the overridden method.
 *
 * @author Radoslav Husar
 * @version April 2012
 */
public abstract class AbstractCommonDebugServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(AbstractCommonDebugServlet.class.getName());
    public static final String KEY = AbstractCommonDebugServlet.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            log.log(Level.INFO, "New DEBUG session created: {0}", session.getId());
            session.setAttribute(KEY, new SerialBean());
        }

        SerialBean bean = (SerialBean) session.getAttribute(KEY);

        resp.setContentType("text/plain");

        int serial = bean.getSerial();
        bean.setSerial(serial + 1);

        // Now store bean in the session
        session.setAttribute(KEY, bean);

        resp.getWriter().println("Serial: " + serial);
        resp.getWriter().println(this.getDebugInfo(req));

        // Invalidate?
        if (req.getParameter(ClusterBenchConstants.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", session.getId());
            session.invalidate();
        }
    }

    @Override
    public String getServletInfo() {
        return "Debug servlet.";
    }

    abstract public String getDebugInfo(HttpServletRequest req);
}
