package validation;

import model.Task;
import model.TaskState;
import storage.TaskTrackerStorage;

public class TaskTrackerValidator {

    private final TaskTrackerStorage taskTrackerStorage;

    public TaskTrackerValidator(TaskTrackerStorage taskTrackerStorage) {
        this.taskTrackerStorage = taskTrackerStorage;
    }

    public boolean validateTaskCreation(String taskName, String personName){
        if (taskTrackerStorage.getTaskByNameAndOwner(taskName,personName)!=null){
            System.out.println("Task '"+taskName+"' is already assigned to "+ personName);
            return false;
        }
        return true;
    }

    public boolean validateTaskStateForStart(String taskName) {
        Task taskForValidation = taskTrackerStorage.getTaskByName(taskName);
        if (taskForValidation != null) {
            if (taskForValidation.getState() == TaskState.OPEN) {
                return true;
            }
            System.out.println("Task '" + taskName + "' is already in progress");
        }else{
            System.out.println("Task '" + taskName + "' does not exist");
        }
        return false;
    }

    public boolean validateTaskStateForStop(String taskName) {
        Task taskForValidation = taskTrackerStorage.getTaskByName(taskName);
        if (taskForValidation != null) {
            if (taskForValidation.getState() == TaskState.IN_PROGRESS) {
                return true;
            }
            System.out.println("Task '" + taskName + "' is already not in progress");
        }else{
            System.out.println("Task '" + taskName + "' does not exist");
        }
        return false;
    }

    public boolean validatePersonCreation(String personName) {
        if (taskTrackerStorage.getPersonByName(personName)!=null){
            System.out.println("Person '" + personName + "' is already present");
            return false;
        }
        return true;
    }

    public boolean validatePersonExistence(String personName) {
        if (taskTrackerStorage.getPersonByName(personName)!=null){
            return true;
        }
        System.out.println("Person '" + personName + "' does not exist");
        return false;
    }

}
