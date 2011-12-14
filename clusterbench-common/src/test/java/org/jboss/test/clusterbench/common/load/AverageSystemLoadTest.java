package org.jboss.test.clusterbench.common.load;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Michal Babacek <mbabacek@redhat.com> 
 *
 */
public class AverageSystemLoadTest {

   @Test
   public void spawnLoadThreadsTest() {
      AverageSystemLoad averageSystemLoad = new AverageSystemLoad();
      int duration = 2000;
      int threads = 2;
      long started = System.currentTimeMillis();
      String result = averageSystemLoad.spawnLoadThreads(threads, duration);
      long ended = System.currentTimeMillis();
      String infoMessgae = "DONE, I was stressing CPU with " + threads + " evil threads for ";
      assertTrue("This message [" + infoMessgae + "] has to be a substring of [" + result + "].", result.contains(infoMessgae));
      long took = ended - started;
      assertTrue("CPU stressing took less time than it should have. Was:" + took + ", expected at least:" + duration, took >= duration);
   }
}
