package org.jboss.test.clusterbench.ejb.stateful;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.test.clusterbench.common.ejb.CommonStatefulSBImpl;

/**
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Stateful
@LocalBean
@SessionScoped
@Clustered
public class LocalStatefulSB extends CommonStatefulSBImpl {
    // Inherit.
}
