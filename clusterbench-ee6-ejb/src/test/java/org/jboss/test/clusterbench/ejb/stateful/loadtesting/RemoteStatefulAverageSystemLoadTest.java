package org.jboss.test.clusterbench.ejb.stateful.loadtesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemoteStatefulAverageSystemLoadTest {

   @Test
   public void spawnLoadThreadsTest() {
      RemoteStatefulAverageSystemLoad remoteStatefulAverageSystemLoad = new RemoteStatefulAverageSystemLoadImpl();
      String result = remoteStatefulAverageSystemLoad.spawnLoadThreads(2, 2000);
      assertEquals("LoadRunner #0 has been created.\nLoadRunner #1 has been created.\n",result);
      result = remoteStatefulAverageSystemLoad.spawnLoadThreads(2, 2000);
      String infoMessgae = "INFO:There are still 2 active runners that have been running for";
      assertTrue("This message ["+infoMessgae+"] has to be a substring of ["+result+"].",result.contains(infoMessgae));
   }
   
}
