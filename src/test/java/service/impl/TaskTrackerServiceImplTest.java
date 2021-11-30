package service.impl;

import dto.TaskDTO;
import model.Person;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.TaskTrackerService;
import storage.TaskTrackerStorage;
import validation.TaskTrackerValidator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskTrackerServiceImplTest {

    @Mock
    TaskTrackerStorage taskTrackerStorage;
    @Mock
    TaskTrackerValidator validator;
    TaskTrackerService taskTrackerService;


    @BeforeEach
    void setup(){
        taskTrackerService = new TaskTrackerServiceImpl(taskTrackerStorage, validator);
    }


    @Test
    void canCreateTaskAndPerson() {
        String personName = "person";
        String taskName = "task";

        given(validator.validatePersonCreation(personName))
                .willReturn(true);
        taskTrackerService.createTask(personName,taskName);

        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);

        verify(taskTrackerStorage).addTask(taskArgumentCaptor.capture());
        verify(taskTrackerStorage).addPerson(personArgumentCaptor.capture());

        Task capturedTask = taskArgumentCaptor.getValue();
        Person capturedPerson = personArgumentCaptor.getValue();

        assertEquals(taskName, capturedTask.getTaskName());
        assertEquals(personName, capturedPerson.getPersonName());
    }

    @Test
    void canCreateTaskForAPerson() {
        String personName = "person";
        String taskName = "task";

        given(validator.validatePersonCreation(personName))
                .willReturn(false);
        given(validator.validateTaskCreation(taskName,personName))
                .willReturn(true);
        given(taskTrackerStorage.getPersonByName(personName))
                .willReturn(new Person(personName));
        taskTrackerService.createTask(personName,taskName);

        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskTrackerStorage).addTask(taskArgumentCaptor.capture());

        Task capturedTask = taskArgumentCaptor.getValue();
        assertEquals(taskName, capturedTask.getTaskName());
    }

    @Test
    void notCreateTaskForAPersonWhenNull() {
        String personName = "person";
        String taskName = "task";

        given(validator.validatePersonCreation(personName))
                .willReturn(false);
        given(validator.validateTaskCreation(taskName,personName))
                .willReturn(false);

        taskTrackerService.createTask(personName,taskName);

        verify(taskTrackerStorage,never()).addTask(any());
    }

    @Test
    void StartTaskWhenValidationTrue() {
        String personName = "something";
        String taskName = "task";

        given(validator.validateTaskStateForStart(taskName))
                .willReturn(true);
        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(new Task(taskName,new Person(personName)));
        taskTrackerService.startTask(taskName);

        verify(taskTrackerStorage).getTaskByName(any());
    }

    @Test
    void notStartTaskWhenValidationFalse() {
        String taskName = "task";

        given(validator.validateTaskStateForStart(taskName))
                .willReturn(false);

        taskTrackerService.startTask(taskName);

        verify(taskTrackerStorage,never()).getTaskByName(any());
    }

    @Test
    void StopTaskWhenValidationTrue() {
        String personName = "something";
        String taskName = "task";

        given(validator.validateTaskStateForStop(taskName))
                .willReturn(true);
        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(new Task(taskName,new Person(personName)));
        taskTrackerService.stopTask(taskName);

        verify(taskTrackerStorage).getTaskByName(any());
    }

    @Test
    void notStopTaskWhenValidationFalse() {
        String taskName = "task";

        given(validator.validateTaskStateForStop(taskName))
                .willReturn(false);

        taskTrackerService.stopTask(taskName);

        verify(taskTrackerStorage,never()).getTaskByName(any());
    }

    @Test
    void getListOfTaskByPerson() {
        Person mircea = new Person("mircea");
        Task task1 = new Task("taskluMircea",mircea);
        Task task2 = new Task("taskluMircea2",mircea);
        Task task3 = new Task("taskluMircea3",mircea);
        Task task4 = new Task("taskluMircea4",mircea);
        ArrayList<Task> tasks= new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        int listSize = 4;

        given(validator.validatePersonExistence(mircea.getPersonName()))
                .willReturn(true);
        given(taskTrackerStorage.getAllTasksByPerson(mircea.getPersonName()))
                .willReturn(tasks);
        ArrayList<TaskDTO> tasksDtos= taskTrackerService
                .getListOfTaskByPerson(mircea.getPersonName());

        assertEquals(listSize, tasksDtos.size());

    }

    @Test
    void dontGetListOfTaskByPersonWhenValidationFalse() {
        Person mircea = new Person("mircea");

        given(validator.validatePersonExistence(mircea.getPersonName()))
                .willReturn(false);
        ArrayList<TaskDTO> tasksDtos= taskTrackerService
                .getListOfTaskByPerson(mircea.getPersonName());

        verify(taskTrackerStorage,never()).getAllTasksByPerson(any());

        assertEquals(0, tasksDtos.size());
    }
}