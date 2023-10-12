/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.common.ejb;

public interface CommonStatefulSB {

    int getSerial();

    int getSerialAndIncrement();

    byte[] getCargo();
}
