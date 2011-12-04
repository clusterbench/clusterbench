package org.jboss.qa.clusterbench.singleton;

import javax.ejb.Remote;

@Remote
public interface RemoteSingletonSB {

    int getSerialAndIncrement();

    byte[] getCargo();
}