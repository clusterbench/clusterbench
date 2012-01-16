package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.Remote;
import org.jboss.test.clusterbench.common.ejb.CommonStatefulSB;

@Remote
public interface RemoteStatefulSB extends CommonStatefulSB {
    // Inherit.
}