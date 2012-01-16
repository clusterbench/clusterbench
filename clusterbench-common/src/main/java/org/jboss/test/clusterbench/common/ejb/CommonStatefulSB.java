package org.jboss.test.clusterbench.common.ejb;

public interface CommonStatefulSB {

    int getSerial();

    int getSerialAndIncrement();

    byte[] getCargo();
}
