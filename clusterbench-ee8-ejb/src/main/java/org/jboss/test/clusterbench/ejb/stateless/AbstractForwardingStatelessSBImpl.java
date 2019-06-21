package org.jboss.test.clusterbench.ejb.stateless;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class AbstractForwardingStatelessSBImpl {

    /*
        java:global/clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB
        java:app/clusterbench-ee8-ejb/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB
        java:module/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB
        java:jboss/exported/clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB
        ejb:clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB
        java:global/clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatelessSBImpl
        java:app/clusterbench-ee8-ejb/RemoteStatelessSBImpl
        java:module/RemoteStatelessSBImpl
     */
    @SuppressWarnings("unchecked")
    private RemoteStatelessSB forward() {
        try {
            Hashtable props = new Hashtable();
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            Context context = new InitialContext(props);
            return (RemoteStatelessSB) context.lookup(
                    "ejb:clusterbench-ee8/clusterbench-ee8-ejb/RemoteStatelessSBImpl!org.jboss.test.clusterbench.ejb.stateless.RemoteStatelessSB"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getNodeName() {
        return forward().getNodeName();
    }
}
