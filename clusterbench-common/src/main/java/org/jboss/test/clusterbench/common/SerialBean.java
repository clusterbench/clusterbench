/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.common;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe serializable bean storing a serial counter and a byte array cargo.
 *
 * @author Radoslav Husar
 */
public class SerialBean implements Serializable {

    private final AtomicInteger serial;
    private byte[] cargo;
    public static final String CARGO_SIZE_KB_SYSTEM_PROPERTY = "org.jboss.test.clusterbench.cargokb";
    public static final int CARGO_SIZE_KB;
    public static final int DEFAULT_CARGO_SIZE_KB = 4;

    static {
        int cargoSizeKB;

        String property = System.getProperty(CARGO_SIZE_KB_SYSTEM_PROPERTY);
        if (property != null) {
            cargoSizeKB = Integer.parseInt(property);
        } else {
            cargoSizeKB = DEFAULT_CARGO_SIZE_KB;
        }

        CARGO_SIZE_KB = cargoSizeKB;
    }

    public SerialBean() {
        this(CARGO_SIZE_KB);
    }

    public SerialBean(int cargoSizeKB) {
        this.serial = new AtomicInteger(0);
        this.cargo = new byte[cargoSizeKB * 1024];
        Random rand = new Random();
        rand.nextBytes(cargo);
    }

    public byte[] getCargo() {
        return cargo;
    }

    public void setCargo(byte[] cargo) {
        this.cargo = cargo;
    }

    public int getSerial() {
        return this.serial.get();
    }

    public int getSerialAndIncrement() {
        return this.serial.getAndIncrement();
    }

    public void setSerial(int serial) {
        this.serial.set(serial);
    }
}
