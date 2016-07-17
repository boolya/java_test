package pack.java.prog.sandbox;

/**
 * Created by Оля on 17.07.2016.
 */

public class Distance {
  public static void main(String[] args) {
    Point p1 = new Point(1,1);
    Point p2 = new Point(3,3);
    System.out.println("ДОМАШНЕЕ ЗАДАНИЕ №2. Расстояние между точками " + "p1" + " с координатами (" + p1.x + "," + p1.y + ") и p2" + " с координатами (" + p2.x + "," + p2.y + ") равно " + distance(p1, p2) + ".");
  }

  public static double distance(Point p1, Point p2){
    return Math.sqrt(Math.pow((p2.x - p1.x),2) + Math.pow((p2.y - p1.y),2));
  }
}
