package org.jboss.qa.clusterbench.cdi;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import org.jboss.qa.clusterbench.common.SerialBean;

@SessionScoped
public class SessionScopedCdiSerialBean extends SerialBean implements Serializable {
}
