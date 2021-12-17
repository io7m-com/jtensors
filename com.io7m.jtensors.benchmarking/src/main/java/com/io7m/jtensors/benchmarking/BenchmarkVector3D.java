/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jtensors.benchmarking;

import com.io7m.jtensors.core.unparameterized.matrices.Matrices4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors4D;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.random;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, warmups = 1)
public class BenchmarkVector3D
{
  @State(Scope.Thread)
  public static class VectorState
  {
    Vector3D v3x;
    Vector3D v3y;
    Vector4D v4x;
    Vector4D v4y;
    Matrix4x4D m4x;
    Matrix4x4D m4y;

    @Setup(Level.Iteration)
    public void setup()
    {
      this.v3x =
        Vector3D.of(random(), random(), random());
      this.v3y =
        Vector3D.of(random(), random(), random());
      this.v4x =
        Vector4D.of(random(), random(), random(), random());
      this.v4y =
        Vector4D.of(random(), random(), random(), random());

      this.m4x =
        Matrix4x4D.of(
          random(),random(),random(),random(),
          random(),random(),random(),random(),
          random(),random(),random(),random(),
          random(),random(),random(),random()
        );
      this.m4y =
        Matrix4x4D.of(
          random(),random(),random(),random(),
          random(),random(),random(),random(),
          random(),random(),random(),random(),
          random(),random(),random(),random()
        );
    }
  }

  @Benchmark
  public int addV3D(
    final Blackhole blackhole,
    final VectorState state)
  {
    blackhole.consume(Vectors3D.add(state.v3x, state.v3y));
    return 1;
  }

  @Benchmark
  public int addV4D(
    final Blackhole blackhole,
    final VectorState state)
  {
    blackhole.consume(Vectors4D.add(state.v4x, state.v4y));
    return 1;
  }

  @Benchmark
  public int mult4x4D(
    final Blackhole blackhole,
    final VectorState state)
  {
    blackhole.consume(Matrices4x4D.multiply(state.m4x, state.m4y));
    return 1;
  }
}
