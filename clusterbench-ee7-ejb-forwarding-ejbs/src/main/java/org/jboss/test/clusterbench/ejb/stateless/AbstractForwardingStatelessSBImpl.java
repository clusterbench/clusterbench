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
            return (RemoteStatelessSB) context.lookup("ejb:" + "clusterbench-ee7/clusterbench-ee7-ejb//RemoteStatelessSBImpl"
                    + "!" + RemoteStatelessSB.class.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getNodeName() {
        return forward().getNodeName();
    }
}
