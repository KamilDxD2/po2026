package agh.po.lab2;
import java.util.ArrayList;
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

        ArrayList<Integer> Args = new ArrayList<>();

        for (String arg : args) { // odpowiednik foreach w JS
            Args.add(Integer.parseInt(arg));
        }

        System.out.println("Twoje typy: " + Args);
        System.out.println("Wylosowane liczby: " + list);
        Args.retainAll(list);
        System.out.print("Liczba trafien: " + Args.size());

    }
}
