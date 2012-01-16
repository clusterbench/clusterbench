package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.SerialBean;

@Stateful
@Clustered
public class LocalStatefulSBImpl extends SerialBean implements LocalStatefulSB {

    // Inherit.
    @Remove
    private void destroy() {
        // Let the container do the work.
    }
}
