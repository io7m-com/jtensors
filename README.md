jtensors
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.jtensors/com.io7m.jtensors.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.jtensors%22)
[![Maven Central (snapshot)](https://img.shields.io/nexus/s/com.io7m.jtensors/com.io7m.jtensors?server=https%3A%2F%2Fs01.oss.sonatype.org&style=flat-square)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/io7m/jtensors/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/jtensors.svg?style=flat-square)](https://codecov.io/gh/io7m-com/jtensors)
![Java Version](https://img.shields.io/badge/21-java?label=java&color=e6c35c)

![com.io7m.jtensors](./src/site/resources/jtensors.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jtensors/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/jtensors/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jtensors/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/jtensors/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jtensors/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/jtensors/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jtensors/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/jtensors/actions?query=workflow%3Amain.windows.temurin.lts)|

## jtensors

The `jtensors` package package implements a set of vector and matrix types,
intended for use in computer graphics software.

## Features

* 2D, 3D, and 4D immutable vector types in `float` and `double` versions.
* 2D, 3D, and 4D immutable vector types in `int` and `long` versions.
* 4D immutable quaternion types in `float` and `double` versions.
* 2x2, 3x3, and 4x4 mutable matrix types in `float` and `double` versions.
* Phantom-typed variants of all types for statically distinguishing between
  semantically different but otherwise type-compatible vectors and matrices.
* Fully documented (Javadoc).
* Massive battery of tests for checking the behaviour of matrix/vector implementations.
* 100% automated unit test coverage.
* [OSGi-ready](https://www.osgi.org/)
* [JPMS-ready](https://en.wikipedia.org/wiki/Java_Platform_Module_System)
* ISC license.

## Usage

See the [documentation](https://www.io7m.com/software/jtensors).

