/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateful;

import org.jboss.test.clusterbench.common.ejb.CommonStatefulSBImpl;

/**
 * @author Radoslav Husar
 */
@Stateful
public class RemoteStatefulSBImpl extends CommonStatefulSBImpl implements RemoteStatefulSB {

    @PostConstruct
    private void ejbInitialize() {
        super.init();
    }

}
