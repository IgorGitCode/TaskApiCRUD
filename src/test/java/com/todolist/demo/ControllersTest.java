package com.todolist.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.demo.controllers.TaskController;
import com.todolist.demo.models.TaskModel;
import com.todolist.demo.services.TaskServices;

@WebMvcTest(TaskController.class)
public class ControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskServices taskServices;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllTasks() throws Exception {
        TaskModel task1 = new TaskModel();
        task1.setTaskName("Estudar jUnit");

        TaskModel task2 = new TaskModel();
        task2.setTaskName("Fazer Compras");

        when(taskServices.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value("Estudar jUnit"))
                .andExpect(jsonPath("$[1].taskName").value("Fazer Compras"));
    };

    @Test
    public void testCreateTask() throws Exception {
        TaskModel newTask = new TaskModel();
        newTask.setTaskName("New Task");

        when(taskServices.createTask(any(TaskModel.class))).thenReturn(newTask);

        mockMvc.perform(post("/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newTask)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.taskName").value("New Task"));
    };

    @Test
    public void testDeleteTask() throws Exception {
        doNothing().when(taskServices).deleteTask(1L);

        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTaskNotFound() throws Exception {
        doThrow(new RuntimeException("Task not found")).when(taskServices).deleteTask(1L);

        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isNotFound());
    };
};