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

package org.jboss.test.clusterbench.common.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Remove;
import org.jboss.test.clusterbench.common.SerialBean;

public class CommonStatefulSBImpl implements CommonStatefulSB {

    private SerialBean bean;
    private static final Logger log = Logger.getLogger(CommonStatefulSBImpl.class.getName());

    @PostConstruct
    private void init() {
        bean = new SerialBean();
        log.log(Level.INFO, "New SFSB created: {0}.", this);
    }

    @Override
    public int getSerial() {
        return bean.getSerial();
    }

    @Override
    public int getSerialAndIncrement() {
        return bean.getSerialAndIncrement();
    }

    @Override
    public byte[] getCargo() {
        return bean.getCargo();
    }

    @Remove
    private void destroy() {
        // Let the container do the work.
    }
}
