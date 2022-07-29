package org.jboss.test.clusterbench.ejb.stateless;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class NonTxForwardingStatelessSBImpl extends AbstractForwardingStatelessSBImpl
        implements RemoteStatelessSB {
}
