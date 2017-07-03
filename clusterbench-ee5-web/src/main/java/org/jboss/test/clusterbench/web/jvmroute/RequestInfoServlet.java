package org.jboss.test.clusterbench.web.jvmroute;

import org.jboss.test.clusterbench.common.jvmroute.CommonRequestInfoServlet;

public class RequestInfoServlet extends CommonRequestInfoServlet {
    private static final long serialVersionUID = -7788063893669970638L;

    @Override
    protected String getServletAPISpecificInfo() {
        return "Intentionally left blank.";
    }
}