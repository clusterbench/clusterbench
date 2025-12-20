/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.web.session;

import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.test.clusterbench.common.SerialBean;

/**
 * SerialBean implementation that uses AtomicInteger for thread-safe concurrent access.
 *
 * @author Radoslav Husar
 */
public class AtomicSerialBean extends SerialBean {

    private final AtomicInteger atomicSerial;

    public AtomicSerialBean() {
        super();
        this.atomicSerial = new AtomicInteger(0);
    }

    public AtomicSerialBean(int cargokb) {
        super(cargokb);
        this.atomicSerial = new AtomicInteger(0);
    }

    @Override
    public int getSerial() {
        return atomicSerial.get();
    }

    @Override
    public void setSerial(int serial) {
        atomicSerial.set(serial);
    }

    @Override
    public int getSerialAndIncrement() {
        return atomicSerial.getAndIncrement();
    }

    /**
     * Thread-safe increment and get operation.
     *
     * @return the updated value after increment
     */
    public int incrementAndGet() {
        return atomicSerial.incrementAndGet();
    }
}
