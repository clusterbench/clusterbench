package org.jboss.qa.clusterbench.session;

import java.io.Serializable;

public class HttpSessionBean implements Serializable {

    private int serial;
    private byte[] cargo;

    public HttpSessionBean() {
        this.serial = 0;
        this.cargo = new byte[4 * 1024];
    }

    public byte[] getCargo() {
        return cargo;
    }

    public void setCargo(byte[] cargo) {
        this.cargo = cargo;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
