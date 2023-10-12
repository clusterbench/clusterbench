/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import jakarta.ejb.Remote;

import org.jboss.test.clusterbench.common.ejb.CommonStatefulSB;

/**
 * @author Radoslav Husar
 */
@Remote
public interface RemoteStatefulSB extends CommonStatefulSB {
    // Inherit.
}