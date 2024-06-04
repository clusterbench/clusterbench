package org.jboss.test.clusterbench.it.tomcat;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Radoslav Husar
 */
public abstract class AbstractTomcatIT {

    @Deployment(testable = false)
    public static Archive<?> deployment() {
        return ShrinkWrap
                .create(ZipImporter.class, "clusterbench.war")
                .importFrom(new File("target/clusterbench-ee10-web-tomcat.war"))
                .as(WebArchive.class);
    }

}
