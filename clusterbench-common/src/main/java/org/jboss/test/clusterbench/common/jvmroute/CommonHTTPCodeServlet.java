package org.jboss.test.clusterbench.common.jvmroute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CommonHTTPCodeServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CommonHTTPCodeServlet.class.getName());
    private CommonJvmRoute commonJvmRoute = new CommonJvmRoute();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int httpCode = 0;
        HttpSession session = request.getSession();
        StringBuilder responseText = new StringBuilder();

        try {
            httpCode = Integer.parseInt(request.getParameter("http_code"));
        } catch (java.lang.NumberFormatException exception) {
            responseText.append("Format exception!\n\n");
            responseText.append(request.toString()+ "\n");
            response.getWriter().print(responseText.toString());
            response.setStatus(500);
            return ;
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        // Gives it a JSESSIONID
        responseText.append("Done. ");
        responseText.append("\n");
        responseText.append("HTTP Code was: ");
        responseText.append(httpCode);
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
        log.log(Level.INFO, responseText.toString());
        response.getWriter().print(responseText.toString());
        response.setStatus(httpCode);
    }

    @Override
    public String getServletInfo() {
        return "Returns a response with HTTP code from parameter e.g. \"?http_code=503\" returns \"503 Service Unavailable\"";
    }
}
