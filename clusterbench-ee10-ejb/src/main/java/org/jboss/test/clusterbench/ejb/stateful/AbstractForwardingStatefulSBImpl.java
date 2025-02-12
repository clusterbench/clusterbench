/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.ejb.stateful;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;

public class AbstractForwardingStatefulSBImpl {
    private RemoteStatefulSB bean;

    private RemoteStatefulSB forward() {
        if (bean == null) {
            try {
                Hashtable<String, String> environment = new Hashtable<>();
                environment.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                Context context = new InitialContext(environment);
                bean = (RemoteStatefulSB) context.lookup("ejb:clusterbench-ee10/clusterbench-ee10-ejb/RemoteStatefulSBImpl!org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB?stateful");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return bean;
    }

    public int getSerial() {
        return forward().getSerial();
    }

    public int getSerialAndIncrement() {
        return forward().getSerialAndIncrement();
    }

    public byte[] getCargo() {
        return forward().getCargo();
    }
}
