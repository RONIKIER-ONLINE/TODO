package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.templete.SuperTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest extends SuperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        assertTrue(truthFlag);
    }

    @Test
    void kill() {
        assertTrue(truthFlag);
    }

    @Test
    void findTaskById() {
        assertTrue(truthFlag);
    }

    @Test
    void findTaskByName() {
        assertTrue(truthFlag);
    }

    @Test
    void saveTask() {
        assertTrue(truthFlag);
    }

    @Test
    void deleteTaskById() {
        assertTrue(truthFlag);
    }

    @Test
    void countTasks() {
        assertTrue(truthFlag);
    }

    @Test
    void allTasks() {
        assertTrue(truthFlag);
    }

    @Test
    public void getMaintanceTasks() {
        assertTrue(truthFlag);
    }

    @Test
    public void tasksRequiredTasks() {
        assertTrue(truthFlag);
    }
}