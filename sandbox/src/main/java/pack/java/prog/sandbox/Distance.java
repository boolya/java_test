package pack.java.prog.sandbox;

/**
 * Created by Оля on 17.07.2016.
 */

public class Distance {
  public static void main(String[] args) {
    Point p = new Point(1,1,3,3);
    System.out.println("ДОМАШНЕЕ ЗАДАНИЕ №2. Расстояние между точками " + "p1" + " с координатами (" + p.x1 + "," + p.y1 + ") и p2" + " с координатами (" + p.x2 + "," + p.y2 + ") равно " + p.distance() + ".");
  }
}
