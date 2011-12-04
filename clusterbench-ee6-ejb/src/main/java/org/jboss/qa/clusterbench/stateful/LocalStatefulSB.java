package org.jboss.qa.clusterbench.stateful;

import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.enterprise.context.SessionScoped;
import org.jboss.qa.clusterbench.common.SerialBean;

@Stateful
@LocalBean
@SessionScoped
//@Clustered
public class LocalStatefulSB {

    private SerialBean bean;

    public LocalStatefulSB() {
        bean = new SerialBean();
    }

    public int getSerialAndIncrement() {
        int serial = bean.getSerial();

        bean.setSerial(serial + 1);

        return serial;
    }
}
