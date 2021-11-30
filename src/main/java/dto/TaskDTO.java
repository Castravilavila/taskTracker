package dto;

import model.Person;
import model.TaskState;

public class TaskDTO {
    private String taskName;
    private TaskState state;
    private Person owner;

    public TaskDTO(String taskName, TaskState state, Person owner) {
        this.owner = owner;
        this.state = state;
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public TaskState getState() {
        return state;
    }

    public Person getOwner() {
        return owner;
    }
}
