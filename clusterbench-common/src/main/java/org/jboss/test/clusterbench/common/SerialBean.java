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

package org.jboss.test.clusterbench.common;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Radoslav Husar
 */
public class SerialBean implements Serializable {

    private int serial;
    private byte[] cargo;
    public static final String CARGOKB_SYSTEM_PROPERTY = "org.jboss.test.clusterbench.cargokb";
    public static final int CARGOKB;
    public static final int DEFAULT_CARGOKB = 4;

    static {
        int cargokb;

        String property = System.getProperty(CARGOKB_SYSTEM_PROPERTY);
        if (property != null) {
            cargokb = Integer.parseInt(property);
        } else {
            cargokb = DEFAULT_CARGOKB;
        }

        CARGOKB = cargokb;
    }

    public SerialBean() {
        this(CARGOKB);
    }

    public SerialBean(int cargokb) {
        this.serial = 0;
        this.cargo = new byte[cargokb * 1024];
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
        return serial;
    }

    public int getSerialAndIncrement() {
        int retVal = this.getSerial();
        serial++;
        return retVal;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
