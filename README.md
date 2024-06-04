# Ste CI

Build status below

![steci](https://github.com/StefanoBelli/bookkeeper/actions/workflows/ste-ci.yml/badge.svg)

## Tested classes

BaseDir=``./bookkeeper-server/src/main/java``

 * ``org.apache.bookkeeper.net.NetworkTopologyImpl``
 * ``org.apache.bookkeeper.bookie.FileInfo``

### Ba-dua is working

* Ba-dua version: 0.4.0
* JDK version: 21 (OpenJDK)
* JRE version: 21 (OpenJDK)
* JVM version: 21 (OpenJDK)
* javac compiler version: 21 (OpenJDK)
* Maven target/source version: 8 (left unchanged)
* Lots of bookkeeper-\* projects got trimmed/disabled
  - Except for bookkeeper-server which is the one being tested here

Change java version using ``update-alternatives`` (ubuntu/debian), ``archlinux-java`` (arch), ...


# Apache original README.md

<img src="https://pbs.twimg.com/profile_images/545716709311520769/piLLa1iC_400x400.png" alt="logo" style="width: 32px;"/>

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.bookkeeper/bookkeeper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.apache.bookkeeper/bookkeeper)

# Apache BookKeeper

Apache BookKeeper is a scalable, fault-tolerant and low latency storage service optimized for append-only workloads.

It is suitable for being used in following scenarios:

- WAL (Write-Ahead-Logging), e.g. HDFS NameNode, Pravega.
- Message Store, e.g. Apache Pulsar.
- Offset/Cursor Store, e.g. Apache Pulsar.
- Object/Blob Store, e.g. storing state machine snapshots.

## Get Started

* Checkout the project [website](https://bookkeeper.apache.org/).
* *Concepts*: Start with the [basic concepts](https://bookkeeper.apache.org/docs/getting-started/concepts) of Apache BookKeeper.
  This will help you to fully understand the other parts of the documentation.
* Follow the [Installation](https://bookkeeper.apache.org/docs/getting-started/installation) guide to set up BookKeeper.

## Documentation

Please visit the [Documentation](https://bookkeeper.apache.org/docs/overview/) from the project website for more information.

## Get In Touch

### Report a Bug

For filing bugs, suggesting improvements, or requesting new features, help us out by [opening a GitHub issue](https://github.com/apache/bookkeeper/issues).

### Need Help?

[Subscribe](mailto:user-subscribe@bookkeeper.apache.org) or [mail](mailto:user@bookkeeper.apache.org) the [user@bookkeeper.apache.org](mailto:user@bookkeeper.apache.org) list - Ask questions, find answers, and also help other users.

[Subscribe](mailto:dev-subscribe@bookkeeper.apache.org) or [mail](mailto:dev@bookkeeper.apache.org) the [dev@bookkeeper.apache.org](mailto:dev@bookkeeper.apache.org) list - Join development discussions, propose new ideas and connect with contributors.

[Join us on Slack](https://communityinviter.com/apps/apachebookkeeper/apache-bookkeeper) - This is the most immediate way to connect with Apache BookKeeper committers and contributors.

## Contributing

We feel that a welcoming open community is important and welcome contributions.

### Contributing Code

1. See our [installation guide](https://bookkeeper.apache.org/docs/next/getting-started/installation/) to get your local environment setup.

2. Take a look at our open issues: [GitHub Issues](https://github.com/apache/bookkeeper/issues).

3. Review our [coding style](https://bookkeeper.apache.org/community/coding-guide/) and follow our [pull requests](https://github.com/apache/bookkeeper/pulls) to learn more about our conventions.

4. Make your changes according to our [contributing guide](https://bookkeeper.apache.org/community/contributing/)
