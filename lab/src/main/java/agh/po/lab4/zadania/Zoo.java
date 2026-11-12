package agh.po.lab4.zadania;

import agh.po.lab4.animals.Animal;
import agh.po.lab4.animals.Dog;
import agh.po.lab4.animals.Parrot;
import agh.po.lab4.animals.Snake;

import java.util.Arrays;
import java.util.Random;

public class Zoo {
    Animal[] animals = new Animal[100];

    public void fill() {
        Random random = new Random();
        Arrays.setAll(animals, i -> switch (random.nextInt(3)) {
            case 0 -> new Parrot("Parrot" + i);
            case 1 -> new Dog("Dog" + i);
            default -> new Snake("Snake" + i);
        });
    }

    public int sumLegs() {
        return Arrays.stream(animals).mapToInt(a -> a.getLegs()).sum();
    }

    public void showAll() {
        for (Animal animal : animals) {
            int legs = animal.getLegs();
            String description = animal.getDescription();
            System.out.println(description);
            animal.makeSound();
        }
    }

    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        zoo.fill();
        zoo.showAll();
        System.out.println("Total legs: " + zoo.sumLegs());
    }
}
