package com.todolist.demo;
import java.util.Optional;

import org.hibernate.annotations.TimeZoneStorage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.todolist.demo.models.TaskModel;
import com.todolist.demo.repositories.TaskRepository;
import com.todolist.demo.services.TaskServices;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Import(TaskServices.class)
@Disabled
public class RepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveTask() {
        TaskModel task = new TaskModel();
        task.setTaskName("Estudar jUnit");
        task.setTaskDescription("learn to test api with Junit");
        task.setTaskCompleted(false);

        TaskModel saved = taskRepository.save(task);

        assertNotNull(saved.getId());
        assertEquals("Estudar jUnit", saved.getTaskName());
        System.out.println(System.getProperty("os.name"));
    };

    @Test
    public void testGetTaskById() {
        TaskModel task = new TaskModel();
        task.setTaskName("Fazer Compras");
        task = taskRepository.save(task);

        Optional<TaskModel> found = taskRepository.findById(task.getId());
        assertTrue(found.isPresent());
        assertEquals("Fazer Compras", found.get().getTaskName());
    }
    
}
