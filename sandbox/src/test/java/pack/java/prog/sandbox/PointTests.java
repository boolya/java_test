package pack.java.prog.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Оля on 23.07.2016.
 */
public class PointTests {

  @Test
  public void testDistanceInteger() {
    Point p = new Point(1,1,3,3);
    Assert.assertEquals(p.distance(), 2.8284271247461903);
  }

  @Test
  public void testDistanceRational() {
    Point p = new Point(1.5,5.76,-1.77,-12.6);
    Assert.assertEquals(p.distance(), 18.648927583107827);
  }

  @Test
  public void testDistanceZero() {
    Point p = new Point(0,0,0,0);
    Assert.assertEquals(p.distance(), 0.00);
  }

  @Test
  public void testDistanceOnOx() {
    Point p = new Point(0,0,10,0);
    Assert.assertEquals(p.distance(), 10.0);
  }

}
