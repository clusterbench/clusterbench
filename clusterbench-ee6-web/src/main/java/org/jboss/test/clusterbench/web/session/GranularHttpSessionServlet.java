package org.jboss.test.clusterbench.web.session;

import javax.servlet.annotation.WebServlet;
import org.jboss.test.clusterbench.common.session.CommonGranularHttpSessionServlet;

@WebServlet(name = "GranularSessionServlet", urlPatterns = {"/granular"})
public class GranularHttpSessionServlet extends CommonGranularHttpSessionServlet {
}
