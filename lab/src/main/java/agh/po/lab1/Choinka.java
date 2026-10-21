package agh.po.lab1;

public class Choinka {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        for (int i = 0; i <= x; i++) {
            System.out.print("\n");
            for (int j = 0; j <= i; j++) {
                System.out.print("*");
            }
        }
    }
}
