package org.jboss.test.clusterbench.ejb.stateless;

import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.ejb.CommonStatelessSB;
import org.jboss.test.clusterbench.common.ejb.CommonStatelessSBImpl;

@Stateless
@Clustered
public class RemoteStatelessSBImpl extends CommonStatelessSBImpl implements CommonStatelessSB {
}
