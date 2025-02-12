/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.common.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.test.clusterbench.common.SerialBean;

/**
 * @author Radoslav Husar
 */
public class CommonStatefulSBImpl implements CommonStatefulSB {

    protected SerialBean bean;
    protected static final Logger LOGGER = Logger.getLogger(CommonStatefulSBImpl.class.getName());

    protected void init() {
        bean = new SerialBean();
        LOGGER.log(Level.INFO, "New SFSB created: {0}.", this);
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

}
