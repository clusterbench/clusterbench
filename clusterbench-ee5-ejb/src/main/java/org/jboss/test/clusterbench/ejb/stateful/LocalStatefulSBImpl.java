package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.Stateful;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.ejb.CommonStatefulSBImpl;

@Stateful
@Clustered
public class LocalStatefulSBImpl extends CommonStatefulSBImpl implements LocalStatefulSB {
    // Inherit.
}
