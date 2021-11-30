package api;

import model.Person;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.TaskTrackerService;
import service.impl.TaskTrackerServiceImpl;
import storage.TaskTrackerStorage;
import validation.TaskTrackerValidator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class TaskTrackerAPITest {
    @Mock
    TaskTrackerStorage taskTrackerStorage;
    @Mock
    TaskTrackerValidator validator;
    @Mock
    TaskTrackerService taskTrackerService;
    TaskTrackerAPI taskTrackerAPI;

    @BeforeEach
    void setup(){
        taskTrackerAPI = new TaskTrackerAPI(taskTrackerStorage,validator,taskTrackerService);
    }

    @Test
    void canCreateTask() {
        String personName = "person";
        String taskName = "task";

        taskTrackerAPI.createTask(personName,taskName);
        verify(taskTrackerService).createTask(personName,taskName);

    }

    @Test
    void startTask() {
        String taskName = "task";
        taskTrackerAPI.startTask(taskName);
        verify(taskTrackerService).startTask(taskName);
    }

    @Test
    void stopTask() {
        String taskName = "task";
        taskTrackerAPI.stopTask(taskName);
        verify(taskTrackerService).stopTask(taskName);
    }

    @Test
    void getAllTasksByPerson() {
        String personName = "person";
        taskTrackerAPI.getAllTasksByPerson(personName);
        verify(taskTrackerService).getListOfTaskByPerson(personName);
    }
}