package org.jboss.test.clusterbench.ejb.singleton;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import org.jboss.test.clusterbench.common.SerialBean;
//import org.jboss.ejb3.annotation.Clustered; -- dropped in AS 7.1 release.

/**
 * Scope interface javax.enterprise.context.SessionScoped is not allowed on singleton enterprise beans.
 * Only @Dependent is allowed on singleton enterprise beans.
 */
@Singleton
@LocalBean
//@Clustered -- dropped in AS 7.1 release.
public class LocalSingletonSB {

    private SerialBean bean;

    public LocalSingletonSB() {
        bean = new SerialBean();
    }

    public int getSerialAndIncrement() {
        int serial = bean.getSerial();

        bean.setSerial(serial + 1);

        return serial;
    }

    @Remove
    private void destroy() {
        bean = null;
    }
}
