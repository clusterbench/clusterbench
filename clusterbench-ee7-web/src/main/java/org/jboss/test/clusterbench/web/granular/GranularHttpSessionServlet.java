/*
 * Copyright 2015 Radoslav Husár
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

package org.jboss.test.clusterbench.web.granular;

import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

import org.jboss.test.clusterbench.common.session.CommonGranularHttpSessionServlet;
import org.wildfly.clustering.web.annotation.Immutable;

/**
 * @author Radoslav Husar
 * @version Dec 2015
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
