/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.granular;

import java.io.Serializable;

import org.wildfly.clustering.web.annotation.Immutable;

/**
 * @author Radoslav Husar
 */
@Immutable
record ImmutableWrapper<T>(T value) implements Serializable {
}
