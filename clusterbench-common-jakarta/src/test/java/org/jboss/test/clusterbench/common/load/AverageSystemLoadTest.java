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
