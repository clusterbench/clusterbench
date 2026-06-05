/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.session;

import org.jboss.test.clusterbench.common.SerialBean;
import org.wildfly.clustering.web.annotation.Immutable;

/**
 * {@link Immutable @Immutable}-annotated wrapper around {@link SerialBean} to prevent WildFly from treating session attribute reads as mutations.
 *
 * @author Radoslav Husar
 */
@Immutable
public class ImmutableSerialBean extends SerialBean {

    public ImmutableSerialBean() {
        super();
    }

    public ImmutableSerialBean(int cargoSizeKB) {
        super(cargoSizeKB);
    }

}
