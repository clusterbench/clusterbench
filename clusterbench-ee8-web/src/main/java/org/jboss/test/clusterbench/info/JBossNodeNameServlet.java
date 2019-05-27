package org.jboss.test.clusterbench.info;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "JBossNodeNameServlet", urlPatterns = { "/jboss-node-name" })
public class JBossNodeNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jbossNodeName = System.getProperty("jboss.node.name");
        resp.getWriter().print(jbossNodeName);
    }
}
