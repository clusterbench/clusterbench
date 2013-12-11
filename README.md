ClusterBench
============

ClusterBench is a simple application that can be deployed in a cluster of JBoss AS 5 (EAP 5), AS 7 (EAP 6) or newer.
Once deployed it is easy to stress (using JMeter, curl, etc) and monitor the performance of the cluster while
at the same time it can be easily checked the correctness of replicated sessions.

Building
--------

It comes in 3 flavors for Java EE 5, 6 and 7:

    $ mvn clean install -Pee6 # default
    $ mvn clean install -Pee5
    $ mvn clean install -Pee5,ee6,ee7 # builds everything; requires JDK 7 or newer

Output files:

    ./clusterbench-ee5-ear/target/clusterbench-ee5.ear
    ./clusterbench-ee6-ear/target/clusterbench-ee6.ear
    ./clusterbench-ee6-ear-passivating/target/clusterbench-ee6-passivating.ear
    ./clusterbench-ee7-ear/target/clusterbench-ee7.ear

Note that bulding EE7 version requires new and yet unreleased version of maven-ear-plugin version 2.9. 
To build it locally do:

    $ git clone https://github.com/apache/maven-plugins.git
    $ cd maven-plugins/maven-ear-plugin
    $ mvn clean install


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


Live Demo
---------

You can try a demo running for free on [OpenShift by Red Hat](https://www.openshift.com/):

[http://clusterbench-rhv.rhcloud.com/clusterbench/](http://clusterbench-rhv.rhcloud.com/clusterbench/)

_Please do not benchmark this instance!_


Servlets
========

Each servlet stresses a different replication logic, but they all produce the same reply:
integer number of times the servlet has been previously invoked within the existing session in a `text/plain` response.
In other words, the first request returns 0 and each following invocation returns number incremented by 1.

Furthermore, each HTTP session carries 4 KB of dummy session data in a byte array.

There are also 2 load driving servlets for memory and CPU usage.


HttpSessionServlet
-------------------
[/clusterbench/session](http://localhost:8080/clusterbench/session)

The "default" servlet. Stores serial number and data in `SerialBean` object which is directly put to `javax.servlet.http.HttpSession`.


CdiServlet
----------
[/clusterbench/cdi](http://localhost:8080/clusterbench/cdi)

Stores a serial number in `@SessionScoped` bean.


LocalEjbServlet
---------------
[/clusterbench/ejbservlet](http://localhost:8080/clusterbench/ejbservlet)

Stores serial and data in `@Stateful` EJB Session bean (SFSB). The EJB is invoked on every request.


GranularSessionServlet
----------------------
[/clusterbench-granular/granular](http://localhost:8080/clusterbench-granular/granular)

Stores serial number and data separately and are both directly put to `javax.servlet.http.HttpSession`.


MemoryUsageServlet
------------------
[/clusterbench/memoryusage?milliseconds=10000&megabytes=500](http://localhost:8080/clusterbench/memoryusage?milliseconds=10000&megabytes=500)

Servlet simulating memory usage of the Java Virtual Machine (JVM). Parameters are `milliseconds` (duration) and `megabytes`.


AverageSystemLoadServlet
------------------------
[/clusterbench/averagesystemload?milliseconds=10000&threads=4](http://localhost:8080/clusterbench/averagesystemload?milliseconds=10000&threads=4)

Servlet simulating CPU load of the cluster node. Parameters are `milliseconds` (duration) and `threads`.



Happy benchmarking!


