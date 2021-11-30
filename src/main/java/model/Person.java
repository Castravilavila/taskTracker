package model;

import java.util.ArrayList;

public class Person {
    private String personName;
    private ArrayList<Task> tasks = new ArrayList<>();

    public Person(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    @Override
    public String toString() {
        return "Person{" +
                ", personName='" + personName + '\'' +
                '}';
    }
}
