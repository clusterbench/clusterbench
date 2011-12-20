package org.jboss.test.clusterbench.ejb.singleton;

import java.io.Serializable;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.SerialBean;

/**
 * Scope interface javax.enterprise.context.SessionScoped is not allowed on singleton enterprise beans.
 * Only @Dependent is allowed on singleton enterprise beans.
 * 
 * Serializable? https://issues.jboss.org/browse/AS7-3018
 * 
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Singleton
@LocalBean
@Clustered
public class LocalSingletonSB implements Serializable {

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
