package org.jboss.test.clusterbench.web.cdi;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import org.jboss.test.clusterbench.common.SerialBean;

@SessionScoped
public class SessionScopedCdiSerialBean extends SerialBean implements Serializable {
}
