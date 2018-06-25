/*
 * Copyright 2013 Radoslav Husár
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.test.clusterbench.common.session;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        session.setAttribute(KEY, bean);

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

    @Override
    public String getServletInfo() {
        return "Servlet using Session to store object with the serial.";
    }
}
