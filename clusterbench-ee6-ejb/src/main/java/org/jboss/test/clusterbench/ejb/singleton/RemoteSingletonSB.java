package org.jboss.test.clusterbench.ejb.singleton;

import javax.ejb.Remote;

@Remote
public interface RemoteSingletonSB {

    int getSerialAndIncrement();

    byte[] getCargo();
}