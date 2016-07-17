package pack.java.prog.sandbox;

/**
 * Created by Оля on 17.07.2016.
 */

public class Point {
  public double x1; //координата точки p1 по оси OX
  public double y1; //координата точки p1 по оси OY

  public double x2; //координата точки p2 по оси OX
  public double y2; //координата точки p2 по оси OY

  public Point(double x1, double y1, double x2, double y2){
    this.x1 = x1;
    this.y1 = y1;

    this.x2 = x2;
    this.y2 = y2;
  }

  public double distance(){
    return Math.sqrt(Math.pow((this.x1 - this.x2),2) + Math.pow((this.y1 - this.y2),2));
  }
}