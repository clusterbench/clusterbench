package org.jboss.test.clusterbench.ejb.stateful.loadtesting;

import javax.ejb.Remote;

/**
 * 
 * @author Michal Babacek <mbabacek@redhat.com> 
 *
 */
@Remote
public interface RemoteStatefulAverageSystemLoad {
   
   String spawnLoadThreads(int numberOfThreads, int milliseconds);
   
}
