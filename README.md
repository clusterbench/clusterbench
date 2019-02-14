ClusterBench
============

[![Build Status](https://travis-ci.org/clusterbench/clusterbench.svg?branch=master)](https://travis-ci.org/clusterbench/clusterbench)

ClusterBench is a simple application that can be deployed in a cluster of JBoss AS 5 (EAP 5), JBoss AS 7 (EAP 6), WildFly 8 and newer.
Once deployed it is easy to stress (using JMeter, curl, etc) and monitor the performance of the cluster while
at the same time it can be easily checked the correctness of replicated sessions.


Building
--------

Clone the Git repository first and switch to the created directory:

	$ git clone https://github.com/clusterbench/clusterbench.git

It comes in 3 flavors for Java EE 5, 6, 7 and 8:

    $ mvn install -Pee8
    $ mvn install -Pee7 # default; requires JDK 7 or newer
    $ mvn install -Pee5,ee6,ee7,ee8 # builds everything; requires JDK 7 or newer
    $ mvn install -Pee6
    $ mvn install -Pee5

Output files:

    ./clusterbench-ee8-ear/target/clusterbench-ee8.ear
    ./clusterbench-ee7-ear/target/clusterbench-ee7.ear
    ./clusterbench-ee6-ear/target/clusterbench-ee6.ear
    ./clusterbench-ee6-ear-passivating/target/clusterbench-ee6-passivating.ear
    ./clusterbench-ee5-ear/target/clusterbench-ee5.ear


Deploying
---------

### JBoss AS 7 / WildFly 8 and newer

You can use the `deploy` target of the [WildFly Maven Plugin](https://docs.jboss.org/wildfly/plugins/maven/latest/deploy-mojo.html) to deploy to your running instance by running:

    $ mvn wildfly:deploy

which will deploy the resulting EAR to the running server.

To do this manually, copy `clusterbench-ee7.ear` (or `clusterbench-ee6.ear` for AS 7) to server's deployments directory
and start the standalone server in the HA mode:

	$ cd ~/wildfly-8.0.0.Final
	$ cp ~/clusterbench-ee6.ear standalone/deployments/
	$ ./bin/standalone.sh -c standalone-ha.xml

You can also use the CLI to do so by starting the server, connecting with CLI and using `deploy` command:

	$ cd ~/wildfly-8.0.0.Final
	$ ./bin/standalone.sh -c standalone-ha.xml

Then connect with the CLI:

	$ ./bin/jboss-cli.sh -c
	[standalone@localhost:9990 /] deploy ~/clusterbench-ee7.ear


If you prefer GUI, you can start the server and navigate to [http://localhost:9990/](http://localhost:9990/)
and follow the instructions.


### Tomcat

To deploy the Tomcat variant of clusterbench, copy the following `war` file into Tomcat installation `webapps/` directory:

    $ cp ~/clusterbench-ee7-web/target/clusterbench-ee7-web-tomcat.war webapps/

Note that CDI, debug, EJB, granular, JSF servlets are unsupported on Tomcat and not bundled in the `war`.


### JBoss AS 5

Copy `clusterbench-ee5.ear` to your server's deploy directory, e.g.:

	$ cp clusterbench-ee5.ear ~/jboss-5.0.0/server/all/deploy/



Scenario Servlets
-----------------

Each servlet stresses a different replication logic, but they all produce the same reply:
number of times (integer) the servlet has been previously invoked within the existing session in a `text/plain` response.
In other words, the first request returns 0 and each following invocation returns number incremented by 1.

Furthermore, each HTTP session carries 4 KB of dummy session data in a byte array.

##### HttpSessionServlet

[/clusterbench/session](http://localhost:8080/clusterbench/session)

The 'default' servlet. Stores serial number and data in `SerialBean` object (POJO) which is directly stored in `javax.servlet.http.HttpSession`.


##### CdiServlet

[/clusterbench/cdi](http://localhost:8080/clusterbench/cdi)

Stores a serial number in `@SessionScoped` bean.


##### LocalEjbServlet

[/clusterbench/ejbservlet](http://localhost:8080/clusterbench/ejbservlet)

Stores serial and data in `@Stateful` EJB Session bean (SFSB). The EJB is then invoked on every request.


##### GranularSessionServlet

[/clusterbench-granular/granular](http://localhost:8080/clusterbench-granular/granular)

Stores serial number and data separately and are both directly put to `javax.servlet.http.HttpSession`.
The byte array is never changed therefore this can be used to test effectiveness of using granular session replication.


Load Servlets
-------------

There are also two oad generating Servlets for memory and CPU usage. These Servlets simulate load on the target system. These can be
used to test the load-balancing mechanism of the reverse proxy.


##### MemoryUsageServlet

[/clusterbench/memoryusage?milliseconds=10000&megabytes=500](http://localhost:8080/clusterbench/memoryusage?milliseconds=10000&megabytes=500)

Servlet simulating memory usage of the Java Virtual Machine (JVM). Parameters are `milliseconds` (duration) and `megabytes`.


##### AverageSystemLoadServlet

[/clusterbench/averagesystemload?milliseconds=10000&threads=4](http://localhost:8080/clusterbench/averagesystemload?milliseconds=10000&threads=4)

Servlet simulating CPU load of the cluster node. Parameters are `milliseconds` (duration) and `threads`.


PROFILES
-------------

There a several profiles to test specific scenarios where the build needs to be customized.


### offload

This profile produces a build to tests session offloading to a remote cache named "web.offload":

    $ mvn install -P ee7,offload -DskipTests

NOTE: this build is currently available only in the ee7 profile!


### offload-db

This profile produces a build to tests session offloading to a remote cache named "web.offload"; the names of the modules are shortened to generate short database names:

    $ mvn install -P ee7,offload-db -DskipTests

NOTE: this build is currently available only in the ee7 profile!


### forwarding-ejbs

This profile produces a build to tests a 2 cluster scenario where the EJBs in the first cluster forward requests to EJBs in the second cluster:

    $ mvn install -P ee7,forwarding-ejbs -DskipTests

NOTE: this build is currently available only in the ee7 profile!


### shared-sessions

This profile produces a build to tests a shared sessions scenario where two WAR in the same EAR share HTTP sessions:

    $ mvn install -P ee7,shared-sessions -DskipTests

NOTE: this build is currently available only in the ee7 profile!


### singleton-deployment-specific-descriptor (using singleton-deployment.xml):

This profile produces a build to tests a singleton deployment scenario where one EAR guaranteed to be active on a single node at a time:

    $ mvn install -P ee7,singleton-deployment-specific-descriptor -DskipTests

NOTE: this version uses descriptor `singleton-deployment.xml` to achieve singleton-deployment functionality
NOTE: this build is currently available only in the ee7 profile!


### singleton-deployment-jboss-all (using jboss-all.xml):

This profile produces a build to tests a singleton deployment scenario where one EAR guaranteed to be active on a single node at a time:

    $ mvn install -P ee7,singleton-deployment-jboss-all -DskipTests

NOTE: this version uses descriptor `jboss-all.xml` to achieve singleton-deployment functionality
NOTE: this build is currently available only in the ee7 profile!


Issues
------

Create them on GitHub Issues:

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

