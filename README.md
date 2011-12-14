ClusterBench
============

ClusterBench is, or rather will be, a sample application to be deployed in a cluster of JBoss AS 7 and AS 5. Then it is easy to stress (curl, JMeter, whatnot) and monitor the perfrormance of the cluster and at the same time easily check correctness of replicated sessions.


Building
========

It comes in 2 flavors for Java EE 5 and 6:

$ mvn clean install -Pee6 # default
$ mvn clean install -Pee5
$ mvn clean install -Pee5,ee6 # build both

Output files:
./clusterbench-ee5-ear/target/clusterbench-ee5.ear
./clusterbench-ee6-ear/target/clusterbench-ee6.ear


Issues
======

Create them on GitHub:
https://github.com/rhusar/clusterbench/issues
