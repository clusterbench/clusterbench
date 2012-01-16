package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.Local;
import org.jboss.test.clusterbench.common.ejb.CommonStatefulSB;

@Local
public interface LocalStatefulSB extends CommonStatefulSB {
    // Inherit.
}