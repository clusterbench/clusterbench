package org.jboss.test.clusterbench.web.jvmroute;

import org.jboss.test.clusterbench.common.jvmroute.CommonHTTPCodeServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "HTTPCodeServlet", urlPatterns = {"/httpcode", "/httpcode/*"})
public class HTTPCodeServlet extends CommonHTTPCodeServlet {
    // Silence is golden
}