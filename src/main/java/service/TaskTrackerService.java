package service;

import dto.TaskDTO;
import java.util.ArrayList;

public interface TaskTrackerService {
    void createTask(String personName, String taskName);
    void startTask(String taskName);
    void stopTask(String taskName);
    ArrayList<TaskDTO> getListOfTaskByPerson(String personName);
}
