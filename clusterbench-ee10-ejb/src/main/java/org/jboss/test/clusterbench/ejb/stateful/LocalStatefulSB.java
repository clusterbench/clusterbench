/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;

import org.jboss.test.clusterbench.common.ejb.CommonStatefulSBImpl;

/**
 * @author Radoslav Husar
 */
@Stateful
@LocalBean
@SessionScoped
public class LocalStatefulSB extends CommonStatefulSBImpl {
    // Inherit.
}
