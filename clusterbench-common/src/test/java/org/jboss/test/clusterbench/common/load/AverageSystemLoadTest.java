/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.common.load;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Michal Babacek
 */
public class AverageSystemLoadTest {

    @Test
    public void spawnLoadThreadsTest() {
        AverageSystemLoad averageSystemLoad = new AverageSystemLoad();
        int duration = 2000;
        int threads = 2;
        long started = System.currentTimeMillis();
        String result = averageSystemLoad.spawnLoadThreads(threads, duration);
        long ended = System.currentTimeMillis();
        String infoMessage = "DONE, I was stressing CPU with " + threads + " evil threads for ";
        assertTrue("This message [" + infoMessage + "] has to be a substring of [" + result + "].", result.contains(infoMessage));
        long took = ended - started;
        assertTrue("CPU stressing took less time than it should have. Was:" + took + ", expected at least:" + duration, took >= duration);
    }
}
