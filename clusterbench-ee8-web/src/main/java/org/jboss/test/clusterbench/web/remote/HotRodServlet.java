/*
 * Copyright 2018 Radoslav Husar
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

package org.jboss.test.clusterbench.web.remote;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.infinispan.client.hotrod.RemoteCacheContainer;

/**
 * @author Radoslav Husar
 */
@WebServlet(name = "HotRodServlet", urlPatterns = { "/hotrod" })
public class HotRodServlet extends HttpServlet {

    @Resource(lookup = "java:jboss/infinispan/remote-container/web-sessions")
    private RemoteCacheContainer hotrod;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String sessionId = req.getSession().getId();
        Integer i = (Integer) hotrod.getCache().get(sessionId);
        if (i == null) {
            i = 0;
        }
        resp.getWriter().write(i.toString());
        hotrod.getCache().put(sessionId, i + 1);
    }
}
