/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateless;

import jakarta.ejb.Remote;

import org.jboss.test.clusterbench.common.ejb.CommonStatelessSB;

/**
 * @author Radoslav Husar
 */
@Remote
public interface RemoteStatelessSB extends CommonStatelessSB {
}