package storage;

import model.Person;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class TaskTrackerStorageTest {
    TaskTrackerStorage taskTrackerStorage = new TaskTrackerStorage();

    @BeforeEach
    public void init() {
        Person person = new Person("Mircea");
        Person person2 = new Person("Ion");
        Task task = new Task("MirceaTask",person);
        Task task2 = new Task("IonTask",person2);
        Task task3 = new Task("AlsoMirceaTask",person);
        person.addTask(task);
        person.addTask(task3);
        taskTrackerStorage.addTask(task);
        taskTrackerStorage.addTask(task2);
        taskTrackerStorage.addTask(task3);
        taskTrackerStorage.addPerson(person);
        taskTrackerStorage.addPerson(person2);
    }


    @Test
    public void correctTaskNameShouldReturnTask(){
        String correctTaskName = "MirceaTask";
        Task taskResult = taskTrackerStorage.getTaskByName(correctTaskName);
        assertEquals(correctTaskName,taskResult.getTaskName());
    }

    @Test
    public void incorrectTaskNameShouldReturnNull(){
        String incorrectTaskName = "nonExistentTask";
        Task taskResult = taskTrackerStorage.getTaskByName(incorrectTaskName);
        assertEquals(null,taskResult);
    }

    @Test
    public void correctTaskNameAndOwnerShouldReturnTask(){
        String correctTaskName = "MirceaTask";
        String correctPersonName = "Mircea";
        Task taskResult = taskTrackerStorage
                .getTaskByNameAndOwner(correctTaskName,correctPersonName);
        assertEquals(correctTaskName,taskResult.getTaskName());
        assertEquals(correctPersonName,taskResult.getOwner().getPersonName());
    }
    @Test
    public void incorrectTaskNameAndOwnerShouldReturnNull(){
        String incorrectTaskName = "MirceaTask112";
        String incorrectPersonName = "Mircea112";
        Task taskResult = taskTrackerStorage
                .getTaskByNameAndOwner(incorrectTaskName,incorrectPersonName);
        assertEquals(null,taskResult);

    }
    @Test
    public void incorrectTaskNameButCorrectOwnerShouldReturnNull(){
        String incorrectTaskName = "MirceaTask112";
        String incorrectPersonName = "Mircea";
        Task taskResult = taskTrackerStorage
                .getTaskByNameAndOwner(incorrectTaskName,incorrectPersonName);
        assertEquals(null,taskResult);

    }
    @Test
    public void correctTaskNameButIncorrectOwnerShouldReturnNull(){
        String incorrectTaskName = "MirceaTask";
        String incorrectPersonName = "Mircea112";
        Task taskResult = taskTrackerStorage
                .getTaskByNameAndOwner(incorrectTaskName,incorrectPersonName);
        assertEquals(null,taskResult);

    }

    @Test
    public void correctPersonNameShouldReturnPerson(){
        String correctPersonName = "Ion";
        Person person = taskTrackerStorage.getPersonByName(correctPersonName);
        assertEquals(correctPersonName,person.getPersonName());
    }

    @Test
    public void IncorrectPersonNameShouldReturnNull(){
        String incorrectPersonName = "Ion112";
        Person person = taskTrackerStorage.getPersonByName(incorrectPersonName);
        assertEquals(null,person);
    }

    @Test
    public void correctPersonNameShouldReturnAllPersonTasks(){
        String correctPersonName = "Mircea";
        ArrayList<Task> tasks = taskTrackerStorage
                .getAllTasksByPerson(correctPersonName);
        assertEquals(2,tasks.size());
    }

    @Test
    public void incorrectPersonNameShouldReturnNoTasks(){
        String correctPersonName = "Mircea1123";
        ArrayList<Task> tasks = taskTrackerStorage
                .getAllTasksByPerson(correctPersonName);
        assertEquals(0,tasks.size());
    }
}