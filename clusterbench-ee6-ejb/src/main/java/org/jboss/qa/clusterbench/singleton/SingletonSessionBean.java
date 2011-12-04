package org.jboss.qa.clusterbench.singleton;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import org.jboss.qa.clusterbench.common.SerialBean;

@Singleton
@LocalBean
public class SingletonSessionBean {

    private SerialBean bean;

    public SingletonSessionBean() {
        bean = new SerialBean();
    }

    public int getSerial() {
        int serial = bean.getSerial();

        bean.setSerial(serial + 1);

        return serial;
    }
}
