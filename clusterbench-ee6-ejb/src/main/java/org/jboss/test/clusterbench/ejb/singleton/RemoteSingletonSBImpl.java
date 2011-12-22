package org.jboss.test.clusterbench.ejb.singleton;

import java.io.Serializable;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
//import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.SerialBean;

/**
 * TODO: Remove Serializable once AS7-3018 is fixed.
 * 
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Singleton
@LocalBean
//@Clustered -- JBAS014549: @Clustered annotation is currently not supported for singleton EJB.
public class RemoteSingletonSBImpl implements RemoteSingletonSB, Serializable {

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
