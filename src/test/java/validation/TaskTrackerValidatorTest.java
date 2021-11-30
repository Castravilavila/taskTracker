package validation;

import model.Person;
import model.Task;
import model.TaskState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.TaskTrackerStorage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskTrackerValidatorTest {

    @Mock
    TaskTrackerStorage taskTrackerStorage;
    TaskTrackerValidator taskTrackerValidatorUnderTest;

    @BeforeEach
    void setup(){
        taskTrackerValidatorUnderTest = new TaskTrackerValidator(taskTrackerStorage);
    }

    @Test
    void canValidateTaskCreation() {
        String personName = "person";
        String taskName = "task";

        given(taskTrackerStorage.getTaskByNameAndOwner(taskName,personName))
                .willReturn(new Task(taskName,new Person(personName)));
        boolean shouldBeFalse = taskTrackerValidatorUnderTest
                .validateTaskCreation(taskName,personName);

        assertEquals(false,shouldBeFalse);
    }

    @Test
    void notValidateTaskCreation() {
        String personName = "person";
        String taskName = "task";

        given(taskTrackerStorage.getTaskByNameAndOwner(taskName,personName))
                .willReturn(null);
        boolean shouldBeTrue = taskTrackerValidatorUnderTest
                .validateTaskCreation(taskName,personName);

        assertEquals(true,shouldBeTrue);
    }

    @Test
    void canValidateTaskStateForStart() {
        String personName = "person";
        String taskName = "task";
        Task task = new Task(taskName, new Person(personName));

        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(task);
        boolean shouldBeTrue = taskTrackerValidatorUnderTest
                .validateTaskStateForStart(taskName);

        assertEquals(true,shouldBeTrue);
    }

    @Test
    void notValidateTaskStateForStart() {
        String personName = "person";
        String taskName = "task";
        Task task = new Task(taskName, new Person(personName));

        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(null);
        boolean shouldBeFalse = taskTrackerValidatorUnderTest
                .validateTaskStateForStart(taskName);

        assertEquals(false,shouldBeFalse);
    }

    @Test
    void notValidateTaskStateForStartWithIncorrectState() {
        String personName = "person";
        String taskName = "task";
        Task task = new Task(taskName, new Person(personName));
        task.setState(TaskState.IN_PROGRESS);
        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(task);
        boolean shouldBeFalse = taskTrackerValidatorUnderTest
                .validateTaskStateForStart(taskName);

        assertEquals(false,shouldBeFalse);
    }

    @Test
    void canValidateTaskStateForStop() {
        String personName = "person";
        String taskName = "task";
        Task task = new Task(taskName, new Person(personName));
        task.setState(TaskState.IN_PROGRESS);
        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(task);
        boolean shouldBeTrue = taskTrackerValidatorUnderTest
                .validateTaskStateForStop(taskName);

        assertEquals(true,shouldBeTrue);
    }

    @Test
    void notValidateTaskStateForStopWithWrongState() {
        String personName = "person";
        String taskName = "task";
        Task task = new Task(taskName, new Person(personName));

        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(task);
        boolean shouldBeFalse = taskTrackerValidatorUnderTest
                .validateTaskStateForStop(taskName);

        assertEquals(false,shouldBeFalse);
    }

    @Test
    void notValidateTaskStateForStopWhenNull() {
        String taskName = "task";

        given(taskTrackerStorage.getTaskByName(taskName))
                .willReturn(null);
        boolean shouldBeFalse = taskTrackerValidatorUnderTest
                .validateTaskStateForStop(taskName);

        assertEquals(false,shouldBeFalse);
    }

    @Test
    void canValidatePersonCreation() {
        String personName = "someone";

        given(taskTrackerStorage.getPersonByName(personName))
                .willReturn(null);
        boolean shouldBeTrue = taskTrackerValidatorUnderTest
                .validatePersonCreation(personName);

        assertEquals(true,shouldBeTrue);
    }

    @Test
    void notValidatePersonCreation() {
        String personName = "someone";

        given(taskTrackerStorage.getPersonByName(personName))
                .willReturn(new Person(personName));
        boolean shouldBeFalse = taskTrackerValidatorUnderTest
                .validatePersonCreation(personName);

        assertEquals(false,shouldBeFalse);
    }

    @Test
    void canValidatePersonExistence() {
        String personName = "someone";

        given(taskTrackerStorage.getPersonByName(personName))
                .willReturn(new Person(personName));
        boolean shouldBeTrue = taskTrackerValidatorUnderTest
                .validatePersonExistence(personName);

        assertEquals(true,shouldBeTrue);
    }

    @Test
    void cannotValidatePersonExistence() {
        String personName = "someone";

        given(taskTrackerStorage.getPersonByName(personName))
                .willReturn(null);
        boolean shouldBeFalse = taskTrackerValidatorUnderTest
                .validatePersonExistence(personName);

        assertEquals(false,shouldBeFalse);
    }
}