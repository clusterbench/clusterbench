package org.jboss.test.clusterbench.web.session;

import javax.servlet.annotation.WebServlet;
import org.jboss.test.clusterbench.common.session.CommonHttpSessionServlet;

@WebServlet(name = "HttpSessionServlet", urlPatterns = {"/session"})
public class HttpSessionServlet extends CommonHttpSessionServlet {
}
