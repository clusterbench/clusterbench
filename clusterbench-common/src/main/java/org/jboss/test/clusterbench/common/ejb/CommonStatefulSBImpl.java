/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
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
