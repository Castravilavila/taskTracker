package service.impl;

import dto.TaskDTO;
import model.Person;
import model.Task;
import model.TaskState;
import service.TaskTrackerService;
import storage.TaskTrackerStorage;
import validation.TaskTrackerValidator;

import java.util.ArrayList;
import java.util.List;

public class TaskTrackerServiceImpl implements TaskTrackerService {
    TaskTrackerStorage taskTrackerStorage;
    TaskTrackerValidator validator;

    public TaskTrackerServiceImpl(TaskTrackerStorage taskTrackerStorage, TaskTrackerValidator validator) {
        this.taskTrackerStorage = taskTrackerStorage;
        this.validator = validator;
    }

    public void createTask(String personName, String taskName) {
        if (validator.validatePersonCreation(personName)){
            createAndAssignTaskAndPerson(personName,taskName);
            System.out.println("Task '"+taskName+"' was created and assigned to newly created "+personName);
        } else if(validator.validateTaskCreation(taskName,personName)){
            createAndAssignTaskToExistingPerson(personName,taskName);
            System.out.println("Task '"+taskName+"' was created and assigned to "+personName);
        }
    }

    public void startTask(String taskName) {
        if (validator.validateTaskStateForStart(taskName)){
            Task task = taskTrackerStorage.getTaskByName(taskName);
            task.setState(TaskState.IN_PROGRESS);
            System.out.println("Task '"+taskName+"' was set in progress");
        }
    }

    public void stopTask(String taskName) {
        if (validator.validateTaskStateForStop(taskName)){
            Task task = taskTrackerStorage.getTaskByName(taskName);
            task.setState(TaskState.OPEN);
            System.out.println("Task '"+taskName+"' was set as open");
        }
    }

    public ArrayList<TaskDTO> getListOfTaskByPerson(String personName) {
        if (validator.validatePersonExistence(personName)){
            ArrayList<Task> tasks= taskTrackerStorage.getAllTasksByPerson(personName);
            for (Task task:tasks){
                System.out.println("Task: "+task.getTaskName()+", state: "
                        +task.getState()+ ", owner: "+task.getOwner().getPersonName());
            }
            return convertTaskListToDTO(tasks);
        }
        System.out.println("no tasks present for "+personName);
        return new ArrayList<>();
    }

    private TaskDTO convertTaskToDTO(Task task){
        return new TaskDTO(task.getTaskName(),task.getState(),task.getOwner());
    }

    private ArrayList<TaskDTO> convertTaskListToDTO(List<Task> tasks){
        ArrayList<TaskDTO> listToReturn = new ArrayList<>();
        for (int i=0;i<tasks.size();i++){
            listToReturn.add(convertTaskToDTO(tasks.get(i)));
        }
        return listToReturn;
    }

    private void createAndAssignTaskAndPerson(String personName, String taskName){
        Person newPerson = new Person(personName);
        Task newTask = new Task(taskName,newPerson);
        newPerson.addTask(newTask);
        taskTrackerStorage.addTask(newTask);
        taskTrackerStorage.addPerson(newPerson);
    }

    private void createAndAssignTaskToExistingPerson(String personName, String taskName){
        Person existingPerson = taskTrackerStorage.getPersonByName(personName);
        Task newTask = new Task(taskName, existingPerson);
        existingPerson.addTask(newTask);
        taskTrackerStorage.addTask(newTask);
    }
}
