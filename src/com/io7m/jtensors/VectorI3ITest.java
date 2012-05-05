package com.io7m.jtensors;

import junit.framework.Assert;

import org.junit.Test;

public class VectorI3ITest
{
  public static int randomNumber()
  {
    return (int) (Integer.MIN_VALUE + (Math.random() * (Integer.MAX_VALUE - Integer.MIN_VALUE)));
  }

  @Test public void testAdd()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorI3ITest.randomNumber();
      final int y0 = VectorI3ITest.randomNumber();
      final int z0 = VectorI3ITest.randomNumber();

      final int x1 = VectorI3ITest.randomNumber();
      final int y1 = VectorI3ITest.randomNumber();
      final int z1 = VectorI3ITest.randomNumber();

      final VectorI3I v0 = new VectorI3I(x0, y0, z0);
      final VectorI3I v1 = new VectorI3I(x1, y1, z1);
      final VectorI3I vr = VectorI3I.add(v0, v1);

      Assert.assertEquals(x0 + x1, vr.x);
      Assert.assertEquals(y0 + y1, vr.y);
      Assert.assertEquals(z0 + z1, vr.z);
    }
  }

  @Test public void testCheckInterface()
  {
    final VectorI3I v = new VectorI3I(1, 2, 3);
    Assert.assertEquals(1, v.getXI());
    Assert.assertEquals(2, v.getYI());
    Assert.assertEquals(3, v.getZI());
  }

  @Test public void testEqualsCorrect()
  {
    final VectorI3I v0 = new VectorI3I(0, 0, 0);
    final VectorI3I v1 = new VectorI3I(0, 0, 0);
    final VectorI3I vz = new VectorI3I(0, 0, 1);
    final VectorI3I vy = new VectorI3I(0, 1, 0);
    final VectorI3I vx = new VectorI3I(1, 0, 0);

    Assert.assertTrue(v0.equals(v0));
    Assert.assertFalse(v0.equals(null));
    Assert.assertFalse(v0.equals(Integer.valueOf(0)));
    Assert.assertFalse(v0.equals(vz));
    Assert.assertFalse(v0.equals(vy));
    Assert.assertFalse(v0.equals(vx));
    Assert.assertTrue(v0.equals(v1));
  }

  @Test public void testHashCodeCorrect()
  {
    final VectorI3I v0 = new VectorI3I(0, 0, 0);
    final VectorI3I v1 = new VectorI3I(0, 0, 0);
    final VectorI3I vz = new VectorI3I(0, 0, 1);
    final VectorI3I vy = new VectorI3I(0, 1, 0);
    final VectorI3I vx = new VectorI3I(1, 0, 0);

    Assert.assertTrue(v0.hashCode() == v0.hashCode());
    Assert.assertTrue(v0.hashCode() == v1.hashCode());
    Assert.assertTrue(v0.hashCode() != vx.hashCode());
    Assert.assertTrue(v0.hashCode() != vy.hashCode());
    Assert.assertTrue(v0.hashCode() != vz.hashCode());
  }

  @Test public void testInit()
  {
    final VectorI3I v = new VectorI3I(1, 2, 3);
    Assert.assertEquals(1, v.x);
    Assert.assertEquals(2, v.y);
    Assert.assertEquals(3, v.z);
  }

  @Test public void testInitDefaultZero()
  {
    final VectorI3I v = new VectorI3I();
    Assert.assertEquals(0, v.x);
    Assert.assertEquals(0, v.y);
    Assert.assertEquals(0, v.z);
  }

  @Test public void testString()
  {
    final VectorI3I v = new VectorI3I(0, 1, 2);
    Assert.assertTrue(v.toString().equals("[VectorI3I 0 1 2]"));
  }

  @Test public void testSubtract()
  {
    for (int index = 0; index < 100; ++index) {
      final int x0 = VectorI3ITest.randomNumber();
      final int y0 = VectorI3ITest.randomNumber();
      final int z0 = VectorI3ITest.randomNumber();

      final int x1 = VectorI3ITest.randomNumber();
      final int y1 = VectorI3ITest.randomNumber();
      final int z1 = VectorI3ITest.randomNumber();

      final VectorI3I v0 = new VectorI3I(x0, y0, z0);
      final VectorI3I v1 = new VectorI3I(x1, y1, z1);
      final VectorI3I vr = VectorI3I.subtract(v0, v1);

      Assert.assertEquals(x0 - x1, vr.x);
      Assert.assertEquals(y0 - y1, vr.y);
      Assert.assertEquals(z0 - z1, vr.z);
    }
  }
}
