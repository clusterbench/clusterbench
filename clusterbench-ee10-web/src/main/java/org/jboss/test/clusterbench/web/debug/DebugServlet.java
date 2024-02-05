/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.debug;

import jakarta.annotation.Resource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.jboss.test.clusterbench.common.debug.AbstractCommonDebugServlet;
import org.jgroups.JChannel;

/**
 * @author Radoslav Husar
 */
@WebServlet(name = "DebugServlet", urlPatterns = { "/debug", "/debug/*" })
public class DebugServlet extends AbstractCommonDebugServlet {

    @Resource(lookup = "java:jboss/jgroups/channel/default")
    private JChannel channel;

    @Override
    public String getContainerSpecificDebugInfo(HttpServletRequest req) {
        StringBuilder info = new StringBuilder();

        // Get current cache nodes
        info.append("Address: ").append(channel.getAddress()).append(System.lineSeparator());
        info.append("Coordinator: ").append(channel.getView().getCoord()).append(System.lineSeparator());
        info.append("Members: ").append(channel.getView().getMembers()).append(System.lineSeparator());

        return info.toString();
    }
}
