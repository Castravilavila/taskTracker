package api;

import dto.TaskDTO;
import service.TaskTrackerService;
import service.impl.TaskTrackerServiceImpl;
import storage.TaskTrackerStorage;
import validation.TaskTrackerValidator;

import java.util.ArrayList;
import java.util.List;

public class TaskTrackerAPI {

    private TaskTrackerStorage taskTrackerStorage;
    private TaskTrackerValidator taskTrackerValidator;
    private TaskTrackerService taskTrackerService;

    public TaskTrackerAPI(TaskTrackerStorage taskTrackerStorage, TaskTrackerValidator taskTrackerValidator, TaskTrackerService taskTrackerService) {
        this.taskTrackerStorage = taskTrackerStorage;
        this.taskTrackerValidator = taskTrackerValidator;
        this.taskTrackerService = taskTrackerService;
    }

    public void createTask(String personName, String taskName){
        taskTrackerService.createTask(personName,taskName);
    }

    public void startTask(String taskName){
        taskTrackerService.startTask(taskName);
    }

    public void stopTask(String taskName){
        taskTrackerService.stopTask(taskName);
    }

    public ArrayList<TaskDTO> getAllTasksByPerson(String personName){
        return taskTrackerService.getListOfTaskByPerson(personName);
    }
}
