package org.jboss.test.clusterbench.web.load;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ReceiveTrafficLoadServlet
 *
 * @author Michal Babacek
 *         <p/>
 *         This simple servlet is used for stressing the server's inbound bandwidth.
 */
public class ReceiveTrafficLoadServlet extends HttpServlet {
    private static final long serialVersionUID = 2446075167724650783L;
    private static final Logger log = Logger.getLogger(ReceiveTrafficLoadServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String data = (String) request.getParameter("data");
        // Thank you very much for this valuable chunk of data.

        if (data != null) {
            log.log(Level.INFO, "DONE");
            response.getWriter().print("DONE;Received;" + data.length() / 1024 + ";KB");
            // pro forma
            data = null;
        } else {
            log.log(Level.WARNING, "WARNING - no \"data\" parameter has been found.");
            response.getWriter().print("ERROR");
        }

    }

    @Override
    public String getServletInfo() {
        return "By invoking ReceiveTrafficLoadServlet, you stress server's inbound bandwidth.";
    }
}
