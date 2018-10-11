package org.jboss.test.clusterbench.ejb.stateful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class AbstractForwardingStatefulSBImpl {
    private static Logger logger = LoggerFactory.getLogger(AbstractForwardingStatefulSBImpl.class);
    private RemoteStatefulSB bean;

    @SuppressWarnings("unchecked")
    private RemoteStatefulSB forward() {
        logger.debug("\n=========================================\n EJB forward \n=========================================\n");
        if (bean == null) {
            try {
                Hashtable props = new Hashtable();
                props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                Context context = new InitialContext(props);
                bean = (RemoteStatefulSB) context.lookup("ejb:" + "clusterbench-ee7/clusterbench-ee7-ejb//RemoteStatefulSBImpl"
                        + "!" + RemoteStatefulSB.class.getName()
                        + "?stateful");
            } catch (Exception e) {
                logger.debug("\n=========================================\n EJB forward error: \n {} \n=========================================\n", e.getMessage());
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
