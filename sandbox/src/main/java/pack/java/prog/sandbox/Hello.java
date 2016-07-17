package pack.java.prog.sandbox;

public class Hello {

  public static void main(String[] args) {
    hellofu("world");
    hellofu("user");
    hellofu("Olya");

    double l = 5;
    System.out.println("Площадь квадрата со стороной " + l + " = " + area(l));

    double a = 4;
    double b = 6;
    System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " = " + area(a, b));
  }

  public static void hellofu(String somebody) {
    System.out.println("Hello, " + somebody + "!");
  }

  public static double area (double len){
    return len * len;
  }

  public static double area (double a, double b){
    return a * b;
  }

}