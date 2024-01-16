/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.debug;

import jakarta.annotation.Resource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.test.clusterbench.common.debug.AbstractCommonDebugServlet;
import org.jgroups.Address;
import org.jgroups.Event;
import org.jgroups.JChannel;
import org.jgroups.stack.IpAddress;

/**
 * @author Radoslav Husar
 */
@WebServlet(name = "DebugServlet", urlPatterns = { "/debug", "/debug/*" })
public class DebugServlet extends AbstractCommonDebugServlet {

    @Resource(lookup = "java:jboss/jgroups/channel/default")
    private JChannel channel;

    @Resource(lookup = "java:jboss/infinispan/container/web")
    private EmbeddedCacheManager container;

    @Override
    public String getContainerSpecificDebugInfo(HttpServletRequest req) {
        StringBuilder info = new StringBuilder();

        // Get current cache nodes
        info.append("Address: ").append(container.getAddress()).append(System.lineSeparator());
        info.append("Coordinator: ").append(container.getCoordinator()).append(System.lineSeparator());
        info.append("Members: ").append(container.getMembers()).append(System.lineSeparator());

        // This makes assumption about the web cache container and the default channel usage
        // since the public Infinispan API doesn't expose this information
        info.append("Physical addresses: ");
        for (Address address : channel.getView().getMembers()) {
            Address physicalAddress = (Address) channel.down(new Event(Event.GET_PHYSICAL_ADDRESS, address));
            IpAddress ipAddress = (IpAddress) physicalAddress;
            info.append(ipAddress.printIpAddress()).append("; ");
        }

        return info.toString();
    }
}
