package org.jboss.test.clusterbench.web.load;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
   private List<LoadRunner> loadRunners = new ArrayList<LoadRunner>();
   private long operationStarted = 0L;
   
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int milliseconds = Integer.parseInt(request.getParameter("milliseconds"));
      int numberOfThreads = Integer.parseInt(request.getParameter("threads")); 
      response.setContentType("text/plain");
      response.getWriter().print(spawnLoadThreads(numberOfThreads, milliseconds));
   }

   @Override
   public String getServletInfo() {
      return "By invoking AverageSystemLoadServlet, you stress CPU.";
   }

   public String spawnLoadThreads(int numberOfThreads, int milliseconds) {
      operationStarted = System.currentTimeMillis();
      for(int i = 0; i < numberOfThreads; i++) {
         loadRunners.add(new LoadRunner(milliseconds));
      }
      //Wait till we are done
      while(stillActiveRunners() != 0) {
         try {
            Thread.sleep(50);
         } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Whoops, our monitoring thread has been interrupted.");
         }
      }
     return "DONE, I was stressing CPU with "+numberOfThreads+" evil threads for "+(System.currentTimeMillis()-operationStarted)+" milliseconds (including warm-up).";
   }
   
   private int stillActiveRunners() {
      int stillActiveRunners = 0;
      for(LoadRunner loadRunner : loadRunners) {
         if(!loadRunner.isComplete()) {
            stillActiveRunners++;
         }
      }
      return stillActiveRunners;
   }
   
   private class LoadRunner implements Runnable {

      private int milliseconds;
      private boolean isComplete = false;
      private Thread runner;
      
      public LoadRunner(int milliseconds) {
         this.milliseconds = milliseconds;
         runner = new Thread(this);
         runner.start();
      }

      @Override
      public void run() {
         // we go with nanos
         long howLong = milliseconds * 1000000L;
         long startTime = System.nanoTime();
         while ((System.nanoTime() - startTime) < howLong) {
           //Le wild empty loop :-P
         }
         setComplete(true);
      }

      public synchronized void setComplete(boolean isComplete) {
         this.isComplete = isComplete;
      }

      public synchronized boolean isComplete() {
         return isComplete;
      }
     
   }
}
