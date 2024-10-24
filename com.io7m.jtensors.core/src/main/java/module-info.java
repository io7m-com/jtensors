/*
 * Copyright © 2017 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

/**
 * Vector/matrix math package (Core)
 */

module com.io7m.jtensors.core
{
  requires static com.io7m.immutables.style;
  requires static org.immutables.value;
  requires static org.osgi.annotation.bundle;
  requires static org.osgi.annotation.versioning;

  requires com.io7m.junreachable.core;

  exports com.io7m.jtensors.core.determinants;
  exports com.io7m.jtensors.core.dotproducts;
  exports com.io7m.jtensors.core.parameterized.matrices;
  exports com.io7m.jtensors.core.parameterized.vectors;
  exports com.io7m.jtensors.core.quaternions;
  exports com.io7m.jtensors.core.unparameterized.matrices;
  exports com.io7m.jtensors.core.unparameterized.vectors;
  exports com.io7m.jtensors.core;

  opens com.io7m.jtensors.core.determinants
    to com.io7m.jtensors.tests;
  opens com.io7m.jtensors.core.dotproducts
    to com.io7m.jtensors.tests;
  opens com.io7m.jtensors.core.parameterized.matrices
    to com.io7m.jtensors.tests;
  opens com.io7m.jtensors.core.parameterized.vectors
    to com.io7m.jtensors.tests;
  opens com.io7m.jtensors.core.quaternions
    to com.io7m.jtensors.tests;
  opens com.io7m.jtensors.core.unparameterized.matrices
    to com.io7m.jtensors.tests;
  opens com.io7m.jtensors.core.unparameterized.vectors
    to com.io7m.jtensors.tests;
  opens com.io7m.jtensors.core
    to com.io7m.jtensors.tests;
}
