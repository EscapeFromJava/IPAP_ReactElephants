package com.example.reactelephants;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Elephant {
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleDoubleProperty weight = new SimpleDoubleProperty();
    SimpleIntegerProperty age = new SimpleIntegerProperty();

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public double getWeight() {
        return weight.get();
    }

    public SimpleDoubleProperty weightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    public Elephant(String name, int age, double weight) {
        setName(name);
        setAge(age);
        setWeight(weight);
    }

    private static String getRandomName() {
        ArrayList<String> listNames = new ArrayList<String>(Arrays.asList(
                "Harry", "Oliver", "Jack", "Charlie", "Thomas", "Jacob", "Alfie", "Riley", "William", "James"));
        return listNames.get(new Random().nextInt(listNames.size()));
    }

    private static int getRandomAge() {
        return new Random().nextInt(100);
    }

    private static double getRandomWeight() {
        return Math.round(new Random().nextDouble(300));
    }

    public static Elephant initializeRandomElephant() {
        return new Elephant(getRandomName(), getRandomAge(), getRandomWeight());
    }

    @Override
    public String toString() {
        return "Elephant{" + getName() + ", " + getAge() + ", " + getWeight() + "}";
    }
}
