/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRED) // this is the default anyway
public class ForwardingStatefulSBImpl extends AbstractForwardingStatefulSBImpl
        implements RemoteStatefulSB  {
}
