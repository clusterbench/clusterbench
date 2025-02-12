/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import jakarta.ejb.Remote;

import org.jboss.test.clusterbench.common.ejb.CommonStatefulSB;

/**
 * The {@link Remote} variant of {@link CommonStatefulSB}.
 *
 * @author Radoslav Husar
 */
@Remote
public interface RemoteStatefulSB extends CommonStatefulSB {
    // Inherit.
}