package org.jboss.test.clusterbench.common.ejb;

public class CommonStatelessSBImpl implements CommonStatelessSB {

    public static final String JBOSS_NODE_NAME_PROPERTY = "jboss.node.name";

    @Override
    public String getNodeName() {
        return System.getProperty(JBOSS_NODE_NAME_PROPERTY);
    }
}
