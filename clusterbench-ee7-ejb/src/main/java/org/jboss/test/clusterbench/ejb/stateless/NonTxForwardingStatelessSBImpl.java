package org.jboss.test.clusterbench.ejb.stateless;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class NonTxForwardingStatelessSBImpl extends AbstractForwardingStatelessSBImpl
        implements RemoteStatelessSB {
}
