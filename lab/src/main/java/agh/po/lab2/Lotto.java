package agh.po.lab1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Lotto {
    public static void main(String[] args) {
        Random r = new Random();
        ArrayList<Integer> list = new ArrayList<>();

        while (list.size() < 6) {
            int number = r.nextInt(49) + 1;
            if (!list.contains(number)) {
                list.add(number);
            }
        }


//        boolean jest = lista.contains(7); // true

        System.out.println("Twoje typy: " + Arrays.toString(args));
        System.out.print("Wylosowane liczby: " + list);
//      System.out.print("Liczba trafien: " + args.retainAll(list);

    }
}
