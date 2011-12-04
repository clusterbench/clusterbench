package org.jboss.qa.clusterbench.stateful;

import javax.ejb.Stateful;
import org.jboss.qa.clusterbench.common.SerialBean;

@Stateful
//@Clustered
public class RemoteStatefulSBImpl implements RemoteStatefulSB {

    private SerialBean bean;

    public RemoteStatefulSBImpl() {
        bean = new SerialBean();
    }

    @Override
    public int getSerial() {
        int serial = bean.getSerial();

        bean.setSerial(serial + 1);

        return serial;
    }

    @Override
    public byte[] getCargo() {
        return bean.getCargo();
    }
}
