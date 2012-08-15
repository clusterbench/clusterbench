package org.jboss.test.clusterbench.ejb.stateless;

import javax.ejb.Remote;
import org.jboss.test.clusterbench.common.ejb.CommonStatelessSB;

@Remote
public interface RemoteStatelessSB extends CommonStatelessSB {
}