package org.jboss.test.clusterbench.web.load;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.test.clusterbench.ejb.stateful.loadtesting.RemoteStatefulAverageSystemLoad;

/**
 * AverageSystemLoadServlet
 * 
 * @author Michal Babacek <mbabacek@redhat.com> 
 *
 * This simple servlet is used for stressing the server's CPU.
 * 
 * Usage:
 * 
 * You may GET e.g. this URL: http://localhost:8080/clusterbench/averagesystemload?milliseconds=20000&threads=4
 * By doing so, there will be 4 threads with evil active-loops created.
 * These threads will be running for 20000 milliseconds.
 * 
 * There are two responses you can get:
 * 
 * 1) If there are no active load runners at the moment:
 * LoadRunner #0 has been created.
 * LoadRunner #1 has been created.
 * LoadRunner #2 has been created.
 * LoadRunner #3 has been created.
 * 
 * 2) If there are active runners (CPU is being stressed at the moment):
 * INFO:There are still 4 active runners that have been running for 700 milliseconds.
 * 
 * That's it. No more functionality.
 */
@WebServlet(name = "AverageSystemLoadServlet", urlPatterns = { "/averagesystemload" })
public class AverageSystemLoadServlet extends HttpServlet {
   private static final Logger log = Logger.getLogger(AverageSystemLoadServlet.class.getName());
   @EJB
   private RemoteStatefulAverageSystemLoad remoteStatefulAverageSystemLoad;
   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
      int numberOfThreads = Integer.parseInt(request.getParameter("threads")); 
      response.setContentType("text/plain");
      response.getWriter().print(remoteStatefulAverageSystemLoad.spawnLoadThreads(numberOfThreads, milliseconds));
   }

   @Override
   public String getServletInfo() {
      return "By invoking AverageSystemLoadServlet, you stress CPU.";
   }

}
