package org.jboss.test.clusterbench.common.load;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Michal Babacek
 *
 * @see org.jboss.test.clusterbench.web.load.AverageSystemLoadServlet
 */
public class AverageSystemLoad {
   private static final Logger log = Logger.getLogger(AverageSystemLoad.class.getName());

   private List<LoadRunner> loadRunners = new ArrayList<LoadRunner>();
   private long operationStarted = 0L;
   
   @SuppressWarnings("SleepWhileInLoop")
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
