package org.jboss.test.clusterbench.ejb.stateful.loadtesting;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;

/**
 * 
 * @author Michal Babacek <mbabacek@redhat.com> 
 *
 */
@Stateful
public class RemoteStatefulAverageSystemLoadImpl implements RemoteStatefulAverageSystemLoad {

   private static final Logger log = Logger.getLogger(RemoteStatefulAverageSystemLoad.class.getName());
   
   private List<LoadRunner> loadRunners = new ArrayList<LoadRunner>();
   private long operationStarted = 0L;

   @Override
   public String spawnLoadThreads(int numberOfThreads, int milliseconds) {
     int stillActiveRunners = stillActiveRunners();
      if(stillActiveRunners == 0) {
         operationStarted = System.currentTimeMillis();
         StringBuilder stringBuilder = new StringBuilder(numberOfThreads);
         for(int i = 0; i < numberOfThreads; i++) {
            loadRunners.add(new LoadRunner(milliseconds));
            stringBuilder.append("LoadRunner #"+i+" has been created.\n");
         }
         return stringBuilder.toString();
      } else {
         return "INFO:There are still "+stillActiveRunners+" active runners that have been running for "+(System.currentTimeMillis()-operationStarted)+" milliseconds.";
      }
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
