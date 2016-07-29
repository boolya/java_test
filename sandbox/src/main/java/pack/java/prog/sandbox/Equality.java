package pack.java.prog.sandbox;

/**
 * Created by Оля on 29.07.2016.
 */
public class Equality {

  public static void main(String[] args){
    String s1 = "firefox";
    String s2 = new String(s1);

    System.out.println(s1 == s2); //сравнение ссылок (физическое сравнение)
    System.out.println(s1.equals(s2)); //сравнение содержимого объектов (логическое сравнение)
  }
}
