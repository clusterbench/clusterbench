/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.cdi;

import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;

import org.jboss.test.clusterbench.common.SerialBean;

/**
 * @author Radoslav Husar
 */
@SessionScoped
public class SessionScopedCdiSerialBean extends SerialBean implements Serializable {
}
