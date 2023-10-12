/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.remote;

import jakarta.annotation.Resource;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
