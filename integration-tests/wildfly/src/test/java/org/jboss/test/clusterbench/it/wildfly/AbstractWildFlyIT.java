/*
 * Copyright The ClusterBench Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.test.clusterbench.it.wildfly;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;

/**
 * Imports previously built enterprise archive (ear) for automatic deployment and allowing resource injection.
 *
 * TODO: move this to a suite scope, so it's only done once per execution.
 *
 * @author Radoslav Husar
 */
public abstract class AbstractWildFlyIT {

    @Deployment(testable = false)
    public static Archive<?> deployment() {
        return ShrinkWrap
                .create(ZipImporter.class, "clusterbench.ear")
                .importFrom(new File("target/clusterbench-ee10-ear.ear"))
                .as(EnterpriseArchive.class);
    }

}
