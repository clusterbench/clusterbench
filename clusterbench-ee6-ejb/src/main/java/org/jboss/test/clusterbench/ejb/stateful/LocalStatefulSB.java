package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.SerialBean;

/**
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Stateful
@LocalBean
@SessionScoped
@Clustered
public class LocalStatefulSB extends SerialBean {

    @Remove
    private void destroy() {
        // Let container do the work.
    }
}
