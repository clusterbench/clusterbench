package org.jboss.test.clusterbench.ejb.singleton;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import org.jboss.test.clusterbench.common.ejb.CommonStatefulSBImpl;

/**
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Singleton
@LocalBean
// @Clustered -- JBAS014549: @Clustered annotation is currently not supported for singleton EJB.
public class RemoteSingletonSBImpl extends CommonStatefulSBImpl implements RemoteSingletonSB {
    // Inherit.
}
