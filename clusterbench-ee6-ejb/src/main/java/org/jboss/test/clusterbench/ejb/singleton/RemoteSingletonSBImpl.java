package org.jboss.test.clusterbench.ejb.singleton;

import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import org.jboss.test.clusterbench.common.SerialBean;

/**
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Singleton
@LocalBean
// @Clustered -- JBAS014549: @Clustered annotation is currently not supported for singleton EJB.
public class RemoteSingletonSBImpl extends SerialBean implements RemoteSingletonSB {

    @Remove
    private void destroy() {
        // Let container do the work.
    }
}
