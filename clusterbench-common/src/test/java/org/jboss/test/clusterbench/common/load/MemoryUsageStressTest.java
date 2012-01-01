package org.jboss.test.clusterbench.common.load;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMException;

import org.junit.Test;

public class MemoryUsageStressTest {
   private static final Logger log = Logger.getLogger(MemoryUsageStressTest.class.getName());

   private MemoryUsageStress memoryUsageStress = null; 
   
   @Test
   public void getFreePercentTest() throws JMException {
      memoryUsageStress = new MemoryUsageStress();
      //100MB for 5000ms
      log.log(Level.INFO,memoryUsageStress.stressSystemMemory(100, 5000));
   }
   
}
