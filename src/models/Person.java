package models;

import exceptions.InvalidPatientException;

abstract class Person {

    private String name;
    private int age;
    private String email;
    private String gender;


    public Person(String name, int age, String email, String gender) {

        // Validation
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidPatientException("Name cannot be empty.");
        }
        if (age <= 0) {
            throw new InvalidPatientException("Age must be a positive number.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidPatientException("Email number cannot be empty.");
        }

        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public abstract void describe();
}
