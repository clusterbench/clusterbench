ClusterBench 4.0 [![CI](https://github.com/clusterbench/clusterbench/workflows/CI/badge.svg)](https://github.com/clusterbench/clusterbench/actions)
================

ClusterBench is a simple application that can be deployed in a cluster of JBoss AS 7 (EAP 6), WildFly 8 and newer.
Once deployed it is easy to stress (using JMeter, curl, etc) and monitor the performance of the cluster while
at the same time it can be easily checked the correctness of replicated sessions.

Support Matrix
--------------

| Branch         | EE Versions                 | Base JDK |
|----------------|-----------------------------|----------|
| `main` / `4.x` | Jakarta EE 10               | 11       |
| `3.x`          | Java EE 5, EE 6, EE 7, EE 8 | 8        |


Building
--------

Clone the Git repository first and switch to its directory:

    $ git clone https://github.com/clusterbench/clusterbench.git

Build the default `main` branch to build the latest Jakarta EE 10 version:

    $ mvn clean install

Output files:

    ./clusterbench-ee10-ear/target/clusterbench-ee10.ear


Deploying
---------

### WildFly 27 and newer

You can use the `deploy` goal of the [WildFly Maven Plugin](https://docs.wildfly.org/wildfly-maven-plugin/) to deploy to your running instance by running:

    $ mvn wildfly:deploy

which will deploy the resulting EAR to the running server.

To do this manually, copy `clusterbench-ee10.ear` to server's deployments directory
and start the standalone server in the HA mode:

	$ cd ~/wildfly-27.0.0.Final
	$ cp ~/clusterbench/clusterbench-ee10-ear/target/clusterbench-ee10.ear standalone/deployments/
	$ ./bin/standalone.sh -c standalone-ha.xml

You can also use the CLI to do so by starting the server, connecting with CLI and using `deploy` command:

	$ cd ~/wildfly-27.0.0.Final
	$ ./bin/standalone.sh -c standalone-ha.xml

Then connect with the CLI:

	$ ./bin/jboss-cli.sh -c
	[standalone@localhost:9990 /] deploy ~/clusterbench/clusterbench-ee10-ear/target/clusterbench-ee10.ear


If you prefer GUI, you can start the server and navigate to [http://localhost:9990/](http://localhost:9990/)
and follow the instructions.


### Tomcat

To deploy the Tomcat variant of clusterbench, copy the following `war` file into Tomcat installation `webapps/` directory:

    $ cp ~/clusterbench-ee10-web/target/clusterbench-ee10-web-tomcat.war webapps/

Note that CDI, debug, EJB, granular, JSF servlets are unsupported on Tomcat and not bundled in the `war`.


Scenario Servlets
-----------------

Each servlet stresses a different replication logic, but they all produce the same reply:
number of times (integer) the servlet has been previously invoked within the existing session in a `text/plain` response.
In other words, the first request returns 0 and each following invocation returns number incremented by 1.

Furthermore, each HTTP session carries 4 KB of dummy session data in a byte array.

##### HttpSessionServlet

[/clusterbench/session](http://localhost:8080/clusterbench/session)

The 'default' servlet. Stores serial number and data in `SerialBean` object (POJO) which is directly stored in `jakarta.servlet.http.HttpSession`.


##### CdiServlet

[/clusterbench/cdi](http://localhost:8080/clusterbench/cdi)

Stores a serial number in `@SessionScoped` bean.


##### LocalEjbServlet

[/clusterbench/ejbservlet](http://localhost:8080/clusterbench/ejbservlet)

Stores serial and data in `@jakarta.ejb.Stateful` Jakarta Enterprise Bean (SFSB). The JEB is then invoked on every request.


##### GranularSessionServlet

[/clusterbench-granular/granular](http://localhost:8080/clusterbench-granular/granular)

Stores serial number and data separately and are both directly put to `jakarta.servlet.http.HttpSession`.
The byte array is never changed therefore this can be used to test effectiveness of using granular session replication.


Load Servlets
-------------

There are also two oad generating Servlets for memory and CPU usage. These Servlets simulate load on the target system. These can be
used to test the load-balancing mechanism of the reverse proxy.

##### AverageSystemLoadServlet

[/clusterbench/averagesystemload?milliseconds=10000&threads=4](http://localhost:8080/clusterbench/averagesystemload?milliseconds=10000&threads=4)

Servlet simulating CPU load of the cluster node. Parameters are `milliseconds` (duration) and `threads`.

Custom Build Profiles
---------------------

There a several profiles to test specific scenarios where the standard build needs to be modified.

### shared-sessions

This profile produces a build to tests a shared sessions scenario where two WARs in the same EAR share HTTP sessions:

    $ mvn install -P shared-sessions -DskipTests


### singleton-deployment-specific-descriptor (using singleton-deployment.xml):

This profile produces a build to tests a singleton deployment scenario where one EAR guaranteed to be active on a single node at a time:

    $ mvn install -P singleton-deployment-specific-descriptor -DskipTests

NOTE: this version uses descriptor `singleton-deployment.xml` to achieve singleton-deployment functionality


### singleton-deployment-jboss-all (using jboss-all.xml):

This profile produces a build to tests a singleton deployment scenario where one EAR guaranteed to be active on a single node at a time:

    $ mvn install -P singleton-deployment-jboss-all -DskipTests

NOTE: this version uses descriptor `jboss-all.xml` to achieve singleton-deployment functionality


### webapp-offload

Uses `distributable-web.xml` inside WAR files, to define the WAR's profile to be used (EAP7-1072).

    $ mvn clean install -P webapp-offload -DskipTests


### webapp-offload-ref

The `distributable-web.xml` inside WAR files, references existing "sm_offload" and "sm_offload_granular" profiles on the server;
Those profiles must be created on the server e.g. with some cli script (EAP7-1072).

    $ mvn clean install -P webapp-offload-ref -DskipTests


### resources-offload

Uses `jboss-all.xml` inside WAR files, to define the WAR's profile to be used (EAP7-1072).

    $ mvn clean install -P resources-offload -DskipTests


### resources-offload-ref

The `jboss-all.xml` inside WAR files, references existing "sm_offload" and "sm_offload_granular" profiles on the server;
Those profiles must be created on the server e.g. with some cli script (EAP7-1072).

    $ mvn clean install -P resources-offload-ref -DskipTests


### short-names

Used in order to shorten name of bundled JARs and WARs within the final EAR file.
Usually used in database tests to produce database tables with short names.
Can be used in conjunction with any of the above `webapp-offload`, `webapp-offload-ref`, `resources-offload`, `resources-offload-ref` profiles.

    $ mvn clean install -P webapp-offload,short-names -DskipTests
    $ mvn clean install -P webapp-offload-ref,short-names -DskipTests
    $ mvn clean install -P resources-offload,short-names -DskipTests
    $ mvn clean install -P resources-offload-ref,short-names -DskipTests


### sso-form

This profile enables form authentication:

    $ mvn clean install -P sso-form -DskipTests

### 2clusters

This profile adds the necessary JEBs to perform call forwarding to a second JEB cluster:

    $ mvn clean install -P 2clusters -DskipTests


Configuration
-------------

The default payload size can be overridden by a system property specifying integer number of bytes to use in a payload:

    $ ./bin/standalone.sh -c standalone-ha.xml -Dorg.jboss.test.clusterbench.cargokb=5

> NOTE: Ensure identical value is specified for all containers in the cluster!


Issues
------

File new issues using GitHub Issues:

[https://github.com/clusterbench/clusterbench/issues](https://github.com/clusterbench/clusterbench/issues)


Contributing
------------

Contributions are welcome! 
Submit pull requests against the upstream repository on GitHub.
Please follow the coding standards to keep the application simple and clean.

[https://github.com/clusterbench/clusterbench](https://github.com/clusterbench/clusterbench)


License
-------

Project is licensed under [Apache License Version 2.0](LICENSE).


Happy benchmarking!
-------------------

