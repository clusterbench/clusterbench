package org.jboss.test.clusterbench.ejb.stateful;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class AbstractForwardingStatefulSBImpl {
    private RemoteStatefulSB bean;

    /*
    	java:global/clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatefulSBImpl!org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB
        java:app/clusterbench-ee8-ejb/RemoteStatefulSBImpl!org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB
        java:module/RemoteStatefulSBImpl!org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB
        java:jboss/exported/clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatefulSBImpl!org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB
        ejb:clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatefulSBImpl!org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB?stateful
        java:global/clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatefulSBImpl
        java:app/clusterbench-ee8-ejb/RemoteStatefulSBImpl
        java:module/RemoteStatefulSBImpl
     */
    @SuppressWarnings("unchecked")
    private RemoteStatefulSB forward() {
        if (bean == null) {
            try {
                Hashtable props = new Hashtable();
                props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                Context context = new InitialContext(props);
                bean = (RemoteStatefulSB) context.lookup(
                        "ejb:clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatefulSBImpl!org.jboss.test.clusterbench.ejb.stateful.RemoteStatefulSB?stateful"
                );
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
