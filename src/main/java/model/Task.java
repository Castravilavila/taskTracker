package model;

public class Task {
    private String taskName;
    private TaskState state= TaskState.OPEN;
    private Person owner;

    public Task(String taskName, Person owner) {
        this.taskName = taskName;
        this.owner = owner;
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

    public void setState(TaskState state) {
        this.state = state;
    }
}
