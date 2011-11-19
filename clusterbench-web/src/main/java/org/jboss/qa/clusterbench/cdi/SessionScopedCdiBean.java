package org.jboss.qa.clusterbench.cdi;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class SessionScopedCdiBean implements Serializable {

    private int serial;

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
