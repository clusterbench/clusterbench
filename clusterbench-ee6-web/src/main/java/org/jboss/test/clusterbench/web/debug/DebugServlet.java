/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.clusterbench.web.debug;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.remoting.transport.Address;
import org.infinispan.remoting.transport.jgroups.JGroupsAddress;
import org.infinispan.remoting.transport.jgroups.JGroupsTransport;
import org.jboss.test.clusterbench.common.debug.AbstractCommonDebugServlet;
import org.jgroups.Event;
import org.jgroups.stack.IpAddress;

/**
 * A simple debug Servlet. Feel free to add anything you should need.
 *
 * @author Radoslav Husar
 */
@WebServlet(name = "DebugServlet", urlPatterns = {"/debug"})
public class DebugServlet extends AbstractCommonDebugServlet {

    @Resource(lookup = "java:jboss/infinispan/container/web")
    private EmbeddedCacheManager container;

    @Override
    public String getDebugInfo(HttpServletRequest req) {
        StringBuilder info = new StringBuilder();

        // Fetch just the node name for now
        info.append("Node name: ").append(System.getProperty("jboss.node.name")).append(System.getProperty("line.separator"));

        // Get the ID and route
        info.append("Session ID: ").append(req.getSession().getId()).append(System.getProperty("line.separator"));

        // Get current cache nodes
        info.append("Members: ").append(container.getMembers()).append(System.getProperty("line.separator"));

        info.append("Physical addresses: ");
        JGroupsTransport transport = (JGroupsTransport) container.getTransport();
        for (Address infinispanWrapAddr : container.getMembers()) {
            JGroupsAddress jgroupsWrapAddr = (JGroupsAddress) infinispanWrapAddr;
            org.jgroups.Address physAddr = (org.jgroups.Address) transport.getChannel().down(new Event(Event.GET_PHYSICAL_ADDRESS, jgroupsWrapAddr.getJGroupsAddress()));
            IpAddress ipAddr = (IpAddress) physAddr;
            info.append(ipAddr.getIpAddress().getHostAddress()).append(":").append(((IpAddress) physAddr).getPort()).append("; ");
        }

        return info.toString();
    }
}
