/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.test.clusterbench.it.wildfly;

import java.util.stream.Stream;

import org.jboss.test.clusterbench.it.shared.AbstractConcurrentSessionServletIT;

/**
 * @author Radoslav Husar
 */
public class ConcurrentSessionServletIT extends AbstractConcurrentSessionServletIT {

    static Stream<String> servletPaths() {
        return Stream.of("/session", "/granular", "/cdi", "/ejbservlet");
    }
}
