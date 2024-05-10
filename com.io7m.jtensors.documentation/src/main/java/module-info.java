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
 * Vector/matrix math package (Documentation)
 */

module com.io7m.jtensors.documentation
{
  requires static org.immutables.value;
  requires static com.io7m.immutables.style;

  requires com.io7m.junreachable.core;
  requires com.io7m.jtensors.core;
  requires com.io7m.jtensors.orthonormalization;
  requires com.io7m.jtensors.storage.api;
  requires com.io7m.jtensors.storage.bytebuffered;
  requires com.io7m.jtensors.storage.heap;
  requires com.io7m.jtensors.generators;

  exports com.io7m.jtensors.documentation;
}