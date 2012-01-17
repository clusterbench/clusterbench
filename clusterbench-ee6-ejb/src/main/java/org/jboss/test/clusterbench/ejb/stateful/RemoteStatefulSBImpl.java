package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.SerialBean;

/**
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Stateful
@Clustered
public class RemoteStatefulSBImpl extends SerialBean implements RemoteStatefulSB {

    @Remove
    private void destroy() {
        // Let container do the work.
    }
}
