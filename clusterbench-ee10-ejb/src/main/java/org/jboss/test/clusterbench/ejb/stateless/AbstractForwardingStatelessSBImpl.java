/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateless;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class AbstractForwardingStatelessSBImpl {

    @SuppressWarnings("unchecked")
    private RemoteStatelessSB forward() {
        try {
            Hashtable props = new Hashtable();
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            Context context = new InitialContext(props);
            return (RemoteStatelessSB) context.lookup(
                    "ejb:clusterbench-ee10/clusterbench-ee10-ejb/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getNodeName() {
        return forward().getNodeName();
    }
}
