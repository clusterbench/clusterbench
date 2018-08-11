package org.jboss.test.clusterbench.ejb.stateless;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED) // this is the default anyway
public class ForwardingStatelessSBImpl extends AbstractForwardingStatelessSBImpl
        implements RemoteStatelessSB {
}
