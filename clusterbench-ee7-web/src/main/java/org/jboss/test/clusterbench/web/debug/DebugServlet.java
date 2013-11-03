/*
 * Copyright 2013 Radoslav Husár
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * A simple debug Servlet. Feel free to add anything you shall need.
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
