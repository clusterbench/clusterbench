package org.jboss.test.clusterbench.ejb.singleton;

import javax.ejb.Remote;
import org.jboss.test.clusterbench.common.ejb.CommonStatefulSB;

@Remote
public interface RemoteSingletonSB extends CommonStatefulSB {
    // Inherit.
}