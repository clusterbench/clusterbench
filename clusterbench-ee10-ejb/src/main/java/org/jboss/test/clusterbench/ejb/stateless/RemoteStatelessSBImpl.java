/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateless;

import jakarta.ejb.Stateless;

import org.jboss.test.clusterbench.common.ejb.CommonStatelessSBImpl;

/**
 * @author Radoslav Husar
 */
@Stateless
public class RemoteStatelessSBImpl extends CommonStatelessSBImpl implements RemoteStatelessSB {
}
