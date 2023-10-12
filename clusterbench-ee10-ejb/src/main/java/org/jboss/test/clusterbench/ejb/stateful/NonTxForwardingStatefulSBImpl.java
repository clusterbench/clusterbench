/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class NonTxForwardingStatefulSBImpl extends AbstractForwardingStatefulSBImpl
        implements RemoteStatefulSB {
}
