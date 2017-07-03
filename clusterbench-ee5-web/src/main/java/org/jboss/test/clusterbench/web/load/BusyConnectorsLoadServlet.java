package org.jboss.test.clusterbench.web.load;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BusyConnectorsLoadServlet
 *
 * @author Michal Babacek
 *         <p/>
 *         This simple servlet is used for stressing the server's connector thread pool.
 *         <p/>
 *         Usage:
 *         <p/>
 *         You may GET e.g. this URL: http://localhost:8080/clusterbench/busyconnectorsload?milliseconds=20000 This invokes a
 *         BusyConnectorsLoadServlet that will wait for the given amount of milliseconds and thus "pretending" some work
 */
public class BusyConnectorsLoadServlet extends HttpServlet {
    private static final long serialVersionUID = 9137679673576887686L;
    private static final Logger log = Logger.getLogger(AverageSystemLoadServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
        // Yes, Thread.sleep() in a servlet :-) The reason is that we want to pretend some thinktime.
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Whoops, our thread has been interrupted.");
        }
        log.log(Level.INFO, "DONE");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("DONE");
    }

    @Override
    public String getServletInfo() {
        return "By invoking BusyConnectorsLoadServlet, you stress connector's busyness.";
    }
}
