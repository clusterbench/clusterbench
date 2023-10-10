/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import jakarta.ejb.Stateful;

import org.jboss.test.clusterbench.common.ejb.CommonStatefulSBImpl;

/**
 * @author Radoslav Husar
 */
@Stateful
public class RemoteStatefulSBImpl extends CommonStatefulSBImpl implements RemoteStatefulSB {
    // Inherit.
}
