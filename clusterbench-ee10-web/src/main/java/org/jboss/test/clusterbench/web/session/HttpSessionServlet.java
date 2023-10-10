/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.session;

import org.jboss.test.clusterbench.common.session.CommonHttpSessionServlet;

import jakarta.servlet.annotation.WebServlet;

/**
 * @author Radoslav Husar
 */
@WebServlet(name = "HttpSessionServlet", urlPatterns = { "/session", "/session/*" })
public class HttpSessionServlet extends CommonHttpSessionServlet {

    @Override
    protected Object createSerialBean() {
        return new ImmutableSerialBean();
    }
}
