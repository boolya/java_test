package pack.java.prog.sandbox;

/**
 * Created by Оля on 17.07.2016.
 */

public class Distance {
  public static void main(String[] args) {
    System.out.println("ДОМАШНЕЕ ЗАДАНИЕ №2. Вычисление расстояния между двумя точками.\n");

    printDistance(1,1,3,3);
    printDistance(0,0,10,0);
    printDistance(1.5,5.76,-1,-12.6);
    printDistance(10,0,0,25);
    printDistance(-5.5,10,13,-0.3);
  }

  public static void printDistance(double p1x, double p1y, double p2x, double p2y) {
    Point p = new Point(p1x, p1y, p2x, p2y);
    System.out.println("Координаты точки p1: " + "(" + p.x1 + ", " + p.y1 + ")");
    System.out.println("Координаты точки p2: " + "(" + p.x2 + ", " + p.y2 + ")");
    System.out.println("Расстояние между точками: " + p.distance() + ".\n");
  }
}
