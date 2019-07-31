package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRED) // this is the default anyway
public class ForwardingStatefulSBImpl extends AbstractForwardingStatefulSBImpl
        implements RemoteStatefulSB  {
}
