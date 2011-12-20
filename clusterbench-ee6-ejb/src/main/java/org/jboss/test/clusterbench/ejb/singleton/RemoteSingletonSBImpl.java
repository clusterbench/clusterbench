package org.jboss.test.clusterbench.ejb.singleton;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import org.jboss.test.clusterbench.common.SerialBean;
//import org.jboss.ejb3.annotation.Clustered; -- dropped in AS 7.1 release.

@Singleton
@LocalBean
//@Clustered -- dropped in AS 7.1 release.
public class RemoteSingletonSBImpl implements RemoteSingletonSB {

    private SerialBean bean;

    public RemoteSingletonSBImpl() {
        bean = new SerialBean();
    }

    @Override
    public int getSerialAndIncrement() {
        int serial = bean.getSerial();
        bean.setSerial(serial + 1);
        return serial;
    }

    @Override
    public byte[] getCargo() {
        return bean.getCargo();
    }

    @Remove
    private void destroy() {
        bean = null;
    }
}
