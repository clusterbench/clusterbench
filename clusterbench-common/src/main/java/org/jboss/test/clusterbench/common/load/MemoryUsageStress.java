package org.jboss.test.clusterbench.common.load;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * Stressing MemoryUsageStress
 * 
 * @author Michal Babacek
 * 
 * @see org.jboss.test.clusterbench.web.load.MemoryUsageServlet
 * 
 *      Some reading: http://docs.oracle.com/javase/6/docs/api/java/lang/management/MemoryUsage.html http://stackoverflow.com/questions/3422557/sun-jvm-committed-virtual-memory-high-consumption
 * 
 */
public class MemoryUsageStress {
   private static final Logger log = Logger.getLogger(MemoryUsageStress.class.getName());
   private static final String FREE_MEMORY_SIZE = "FreePhysicalMemorySize";
   private static final String TOTAL_MEMORY_SIZE = "TotalPhysicalMemorySize";
   private MBeanServer server;
   private ObjectName osMXBeanObjectName;
   private MemoryMXBean memBean;

   public MemoryUsageStress() {
      this.server = ManagementFactory.getPlatformMBeanServer();
      this.memBean = ManagementFactory.getMemoryMXBean();
      try {
         this.osMXBeanObjectName = ObjectName.getInstance(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);
      } catch (MalformedObjectNameException e) {
         log.log(Level.SEVERE, "Can't work with OPERATING_SYSTEM_MXBEAN_NAME", e);
      }
   }

   /**
    * stressSystemMemory
    * 
    * JVM will be forced to put up a specified number of megabytes, thus
    * affecting both memory allocated for JVM already and possible the
    * physical memory as well (in case JVM will have to ask for more memory
    * up to the maxMem constant). 
    * 
    * @param megabytes
    * @param milliseconds
    * @return status before and after the stress
    */
   public String stressSystemMemory(int megabytes, int milliseconds) {
      try {
         long started = System.currentTimeMillis();
         String before = getStats();
         log.log(Level.INFO, "\nBEFORE:" + before);     
         //Allocate as many megabytes as specified
         @SuppressWarnings("unused")
         byte[] rubbish = new byte[1024 * 1024 * megabytes];
         // Sleep for specified time
         Thread.sleep(milliseconds);
         // Dereference rubbish (does not really matter in this context, because this instance will be thrown away anyway), just in case...
         rubbish = null;
         // TODO: Hmm, tell JVM to .gc()? Well, it shall take care of itself...
         String after = getStats();
         log.log(Level.INFO, "\nAFTER: " + after);
         return "BEFORE:" + before + "\n AFTER:" + after + "\nDuration (including warm-up): " + (System.currentTimeMillis() - started) + " milliseconds";
      } catch (JMException e) {
         log.log(Level.SEVERE, "Can't work with OS MXBEAN", e);
      } catch (InterruptedException e) {
         log.log(Level.SEVERE, "Thread sleep has been interrupted.", e);
      }
      return "";
   }

   private <T> T getAttribute(String attribute, Class<T> targetClass) throws JMException {
      return targetClass.cast(this.server.getAttribute(this.osMXBeanObjectName, attribute));
   }

   public long getFreePhysical() throws JMException {
      return getAttribute(FREE_MEMORY_SIZE, Number.class).longValue();
   }

   public long getTotalPhysical() throws JMException {
      return getAttribute(TOTAL_MEMORY_SIZE, Number.class).longValue();
   }

   public MemoryUsage getHeapMem() {
      return memBean.getHeapMemoryUsage();
   }

   public MemoryUsage getNonHeapMem() {
      return memBean.getNonHeapMemoryUsage();
   }
   
   public String getStats() throws JMException {
      long total = getTotalPhysical();
      long totalMB = total >> 20; // like /1024 /1024 ;-)
      long free = getFreePhysical();
      long freeMB = free >> 20;
      int physPercent = (int) (free / (total / 100));
      int runtimeTotal = (int) (Runtime.getRuntime().totalMemory() >> 20);
      int runtimeFree = (int) (Runtime.getRuntime().freeMemory() >> 20);
      int runtimeMax = (int) (Runtime.getRuntime().maxMemory() >> 20);
      int runtimePercent = runtimeTotal / (runtimeMax / 100);
      int heapInt = (int)(getHeapMem().getInit() >> 20);
      int heapUsed = (int)(getHeapMem().getUsed() >> 20);
      int heapCommitted = (int)(getHeapMem().getCommitted() >> 20);
      int heapMax = (int)(getHeapMem().getMax() >> 20);
      int heapUsedOfMax = heapUsed / (heapMax / 100);
      int nonHeapInt = (int)(getNonHeapMem().getInit() >> 20);
      int nonHeapUsed = (int)(getNonHeapMem().getUsed() >> 20);
      int nonHeapCommitted = (int)(getNonHeapMem().getCommitted() >> 20);
      int nonHeapMax = (int)(getNonHeapMem().getMax() >> 20);
      int nonHeapUsedOfMax = nonHeapUsed / (nonHeapMax / 100);
      
      String physicalAndRuntime = String.format("Physical: TOTAL: %dMB, FREE: %dMB (%d%%)\nRuntime:  TOTAL: %dMB, FREE: %dMB, MAX: %dMB, TotalOfMax: %d%%\n",totalMB, freeMB, physPercent, runtimeTotal, runtimeFree, runtimeMax, runtimePercent);
      String heap = String.format("Heap:     INIT: %dMB, USED: %dMB, COMMITTED: %dMB, MAX: %dMB, UsedOfMax: %d%%\n",heapInt, heapUsed, heapCommitted,heapMax,heapUsedOfMax);
      String nonHeap = String.format("NonHeap:  INIT: %dMB, USED: %dMB, COMMITTED: %dMB, MAX: %dMB, UsedOfMax: %d%%",nonHeapInt, nonHeapUsed, nonHeapCommitted,nonHeapMax,nonHeapUsedOfMax);
      return physicalAndRuntime+heap+nonHeap;
   }
}
