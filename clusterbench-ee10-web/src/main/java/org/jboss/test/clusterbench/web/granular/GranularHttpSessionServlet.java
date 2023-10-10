/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.granular;

import java.io.Serializable;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

import org.jboss.test.clusterbench.common.session.CommonGranularHttpSessionServlet;
import org.wildfly.clustering.web.annotation.Immutable;

/**
 * @author Radoslav Husar
 */
@WebServlet(name = "GranularSessionServlet", urlPatterns = { "/granular" })
public class GranularHttpSessionServlet extends CommonGranularHttpSessionServlet {

    @Override
    public void storeCargo(HttpSession session, byte[] cargo) {
        ImmutableWrapper<byte[]> wrap = new ImmutableWrapper<>(cargo);
        session.setAttribute(KEY_CARGO, wrap);
    }

    @Override
    public byte[] loadCargo(HttpSession session) {
        ImmutableWrapper<byte[]> wrap = (ImmutableWrapper<byte[]>) session.getAttribute(KEY_CARGO);
        return wrap.getValue();
    }

    @Immutable
    static class ImmutableWrapper<T> implements Serializable {

        private final T value;

        ImmutableWrapper(T value) {
            this.value = value;
        }

        T getValue() {
            return value;
        }
    }

}
