package org.jboss.test.clusterbench.common.jvmroute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CommonRequestInfoServlet extends HttpServlet {
    private static final long serialVersionUID = -2126246174508889343L;
    private CommonJvmRoute commonJvmRoute = new CommonJvmRoute();
    private static final Logger log = Logger.getLogger(CommonRequestInfoServlet.class.getName());

    protected abstract String getServletAPISpecificInfo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gives it a JSESSIONID
        HttpSession session = request.getSession();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        StringBuilder responseText = new StringBuilder();
        /**
         * == BIG EVIL WARNING ==
         * There are tests out there, parsing for this output...
         */

        responseText.append("Request URI: ");
        responseText.append(request.getRequestURI());
        responseText.append("\n");
        responseText.append("Path info: ");
        responseText.append(request.getPathInfo());
        responseText.append("\n");
        responseText.append("Query string: ");
        responseText.append(request.getQueryString());
        responseText.append("\n");
        responseText.append("Query string UTF-8 decoded: ");
        responseText.append((request.getQueryString() == null) ? "null" : URLDecoder.decode(request.getQueryString(), "UTF-8"));
        responseText.append("\n");
        responseText.append("Remote user: ");
        responseText.append(request.getRemoteUser());
        responseText.append("\n");
        responseText.append("Parameters [key=value]: {");
        @SuppressWarnings("rawtypes") //Meh...
                Map params = request.getParameterMap();
        @SuppressWarnings("rawtypes") Iterator i = params.keySet().iterator();
        while (i.hasNext()) {
            String key = (String) i.next();
            String value = ((String[]) params.get(key))[0];
            responseText.append("[");
            responseText.append(key);
            responseText.append("=");
            responseText.append(value);
            responseText.append("]");
            if (i.hasNext()) responseText.append(" ");
        }
        responseText.append("}\n");
        responseText.append("Headers: {");
        @SuppressWarnings("unchecked") Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            responseText.append(headerName);
            responseText.append("=");
            responseText.append(headerValue);
            if (headerNames.hasMoreElements()) responseText.append(", ");
        }
        responseText.append("}\n");
        responseText.append("Host header: ");
        responseText.append(request.getHeader("Host"));
        responseText.append("\n");
        responseText.append("Character encoding: ");
        responseText.append(request.getCharacterEncoding());
        responseText.append("\n");
        responseText.append("JVM route: ");
        responseText.append(commonJvmRoute.jvmRoute());
        responseText.append("\n");
        responseText.append("Session ID: ");
        responseText.append(session.getId());
        responseText.append("\n");
        responseText.append("Session isNew: ");
        responseText.append(session.isNew());
        responseText.append("\n");
        responseText.append("Session ServletContext: ");
        responseText.append(session.getServletContext());
        responseText.append("\n");
        responseText.append("Servlet specific info: ");
        responseText.append(getServletAPISpecificInfo());
        responseText.append("\n");
        response.getWriter().print(responseText.toString());
        log.log(Level.INFO, responseText.toString());
    }

    @Override
    public String getServletInfo() {
        return "By invoking RequestInfoServlet, you get the various pieces of information importance of which can hardly be exaggerated :-)";
    }
}
