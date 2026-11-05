package agh.po.lab4.animals;

public class Dog extends Animal {
    public Dog(String name) {
        super(name, 4);
    }

    @Override
    public String getDescription() {
        return "Dog " + name + ", legs: " + legs;
    }

    @Override
    public void makeSound() {
        System.out.println(name + ": Woof!");
    }
}
