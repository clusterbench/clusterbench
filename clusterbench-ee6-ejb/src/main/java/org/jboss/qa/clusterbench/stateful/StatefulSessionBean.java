package org.jboss.qa.clusterbench.stateful;

import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import org.jboss.qa.clusterbench.common.SerialBean;

@Stateful
@LocalBean
public class StatefulSessionBean {

    private SerialBean bean;

    public StatefulSessionBean() {
        bean = new SerialBean();
    }

    public int getSerial() {
        int serial = bean.getSerial();

        bean.setSerial(serial + 1);

        return serial;
    }
}
