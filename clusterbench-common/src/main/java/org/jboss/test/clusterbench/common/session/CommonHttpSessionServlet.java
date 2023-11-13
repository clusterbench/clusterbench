/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.common.session;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jboss.test.clusterbench.common.ClusterBenchConstants;
import org.jboss.test.clusterbench.common.SerialBean;

public class CommonHttpSessionServlet extends HttpServlet {

    protected static final Logger log = Logger.getLogger(CommonHttpSessionServlet.class.getName());
    public static final String KEY = CommonHttpSessionServlet.class.getName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            if (req.getParameter(ClusterBenchConstants.CARGOKB) != null && req.getParameter(ClusterBenchConstants.CARGOKB).matches("[0-9]+")) {
                log.log(Level.INFO, "New session created: {0} with {1}kB cargo", new Object[]{session.getId(), req.getParameter(ClusterBenchConstants.CARGOKB)});
                int kargokb = Integer.parseInt(req.getParameter(ClusterBenchConstants.CARGOKB));
                session.setAttribute(KEY, this.createSerialBean(kargokb));
            } else {
                log.log(Level.INFO, "New session created: {0} with {1}kB cargo", new Object[]{session.getId(), SerialBean.DEFAULT_CARGOKB});
                session.setAttribute(KEY, this.createSerialBean());
            }
        } else if (session.getAttribute(KEY) == null) {
            log.log(Level.INFO, "Session is not new, creating SerialBean: {0}", session.getId());
            session.setAttribute(KEY, this.createSerialBean());
        }

        SerialBean bean = (SerialBean) session.getAttribute(KEY);

        resp.setContentType("text/plain");

        // Readonly?
        if (req.getParameter(ClusterBenchConstants.READONLY) != null) {
            resp.getWriter().print(bean.getSerial());
            return;
        }

        int serial = bean.getSerial();
        bean.setSerial(serial + 1);

        // Now store bean in the session
        // Workaround for "WFLY-18727 ATTRIBUTE granularity distributed sessions should always replicate on setAttribute(...)" by wrapping into a new object instance.
        session.setAttribute(KEY, this.wrapSerialBean(bean));

        resp.getWriter().print(serial);

        // Invalidate?
        if (req.getParameter(ClusterBenchConstants.INVALIDATE) != null) {
            log.log(Level.INFO, "Invalidating: {0}", session.getId());
            session.invalidate();
        }
    }

    private Object createSerialBean(int cargokbytes) {
        return new SerialBean(cargokbytes);
    }

    protected Object createSerialBean() {
        return new SerialBean();
    }

    // n.b. all of the CommonHttpSessionServlet will be removed from the common module.
    protected Object wrapSerialBean(SerialBean serialBean) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getServletInfo() {
        return "Servlet using Session to store object with the serial.";
    }
}
