package org.jboss.test.clusterbench.info;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "JBossNodeNameServlet", urlPatterns = { "/jboss-node-name" })
public class JBossNodeNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jbossNodeName = System.getProperty("jboss.node.name");
        resp.getWriter().print(jbossNodeName);
    }
}
