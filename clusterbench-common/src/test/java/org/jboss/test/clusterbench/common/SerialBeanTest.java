/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A very simplistic test to ensure the {@link SerialBean} contract.
 *
 * @author Radoslav Husar
 */
public class SerialBeanTest {

    private SerialBean bean;

    @BeforeEach
    void setUp() {
        bean = new SerialBean();
    }

    @Test
    public void testSerial() {
        assertEquals(0, bean.getSerial(),
                "Initial serial is always 0.");
        assertEquals(0, bean.getSerial(),
                "Subsequent calls to getSerial does not effect the serial.");
        assertEquals(0, bean.getSerialAndIncrement(),
                "Calls to getSerialAndIncrement returns current serial.");
        assertEquals(1, bean.getSerial(),
                "Subsequent calls to getSerial does not effect the serial.");
        assertEquals(1, bean.getSerialAndIncrement(),
                "Calls to getSerialAndIncrement returns current serial.");
        assertEquals(2, bean.getSerialAndIncrement(),
                "Calls to getSerialAndIncrement returns current serial.");

        bean.setSerial(0);

        assertEquals(0, bean.getSerialAndIncrement(),
                "The serial can be reset back to 0.");
        assertEquals(1, bean.getSerialAndIncrement(),
                "Calls to getSerialAndIncrement returns current serial.");
    }

    @Test
    public void testCargo() {
        assertEquals(4 * 1024, bean.getCargo().length,
                "Initial cargo size must be 4K.");

        assertNotEquals(new byte[10], bean.getCargo(),
                "Initial cargo is not an empty array.");

        bean.setCargo(new byte[10]);
        assertEquals(10, bean.getCargo().length,
                "Cargo can be overridden to any size.");
    }

}