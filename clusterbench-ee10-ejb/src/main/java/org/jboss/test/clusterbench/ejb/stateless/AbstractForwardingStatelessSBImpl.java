/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateless;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;

public class AbstractForwardingStatelessSBImpl {

    private RemoteStatelessSB forward() {
        try {
            Hashtable<String, String> environment = new Hashtable<>();
            environment.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            Context context = new InitialContext(environment);
            return (RemoteStatelessSB) context.lookup("ejb:clusterbench-ee10/clusterbench-ee10-ejb/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getNodeName() {
        return forward().getNodeName();
    }

}
