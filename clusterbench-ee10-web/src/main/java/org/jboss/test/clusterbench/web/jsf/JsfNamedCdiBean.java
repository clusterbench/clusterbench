/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.web.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.inject.Named;

import org.jboss.test.clusterbench.common.SerialBean;

/**
 * @author Radoslav Husar
 */
@Named("jsfNamedCdiBean")
@SessionScoped
@FacesConfig
public class JsfNamedCdiBean extends SerialBean {
}
