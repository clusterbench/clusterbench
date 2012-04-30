package org.jboss.test.clusterbench.web.debug;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import org.jboss.test.clusterbench.common.debug.AbstractCommonDebugServlet;

@WebServlet(name = "DebugServlet", urlPatterns = {"/debug"})
public class DebugServlet extends AbstractCommonDebugServlet {

    @Override
    public String getDebugInfo(HttpServletRequest req) {
        StringBuilder info = new StringBuilder();

        // Fetch just the node name for now
        info.append("Node name: ").append(System.getProperty("jboss.node.name")).append(System.getProperty("line.separator"));

        // Get the ID and route
        info.append("Session ID: ").append(req.getSession().getId()).append(System.getProperty("line.separator"));

        return info.toString();
    }
}
