package storage;

import model.Person;
import model.Task;

import java.util.ArrayList;

public class TaskTrackerStorage {

    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Person> persons = new ArrayList<>();

    public Task getTaskByName(String taskName){
        return tasks.stream().filter(t-> t.getTaskName()
                .equalsIgnoreCase(taskName)).findFirst().orElse(null);
    }
    public Task getTaskByNameAndOwner(String taskName, String ownerName){
        return tasks.stream().filter(t -> t.getOwner().
                getPersonName().equalsIgnoreCase(ownerName) &&
                t.getTaskName().equalsIgnoreCase(taskName)).findFirst().orElse(null);
    }

    public Person getPersonByName(String personName){
        return persons.stream().filter(p -> p.getPersonName()
                .equalsIgnoreCase(personName)).findFirst().orElse(null);
    }

    public ArrayList<Task> getAllTasksByPerson(String personName){
        Person searchedPerson = getPersonByName(personName);
        if (searchedPerson!=null) {
            return searchedPerson.getTasks();
        }
        return new ArrayList<>();
    }

    public void addTask(Task task){

        tasks.add(task);
    }

    public void addPerson(Person person){
        persons.add(person);
    }
}
