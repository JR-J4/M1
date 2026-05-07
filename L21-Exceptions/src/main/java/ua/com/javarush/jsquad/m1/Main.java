package ua.com.javarush.jsquad.m1;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        test();

        System.out.println("After main");
    }

    public static void test() {
        test1();

        System.out.println("After call");
    }

    public static void test1() {
        System.out.println("Test 1 start");



//        System.out.println("After call 2");
    }
}