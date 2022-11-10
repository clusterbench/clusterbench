package org.jboss.test.clusterbench.web.debug;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.test.clusterbench.common.ClusterBenchConstants;

/**
 * @author Tommasso Borgato
 * @author Radoslav Husar
 */
@WebServlet(name = "JBossNodeNameServlet", urlPatterns = { "/jboss-node-name" })
public class JBossNodeNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Create session?
        if (req.getParameter(ClusterBenchConstants.CREATE) != null) {
            req.getSession();
        }

        String jbossNodeName = System.getProperty("jboss.node.name");
        resp.getWriter().print(jbossNodeName);
    }
}
