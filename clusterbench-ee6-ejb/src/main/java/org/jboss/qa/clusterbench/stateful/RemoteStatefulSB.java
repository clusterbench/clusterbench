package org.jboss.qa.clusterbench.stateful;

import javax.ejb.Remote;

// The enterprise bean must implement a business interface. That is, remote clients may not access an enterprise bean through a no-interface view.
@Remote
public interface RemoteStatefulSB {

    int getSerial();

    byte[] getCargo();
}