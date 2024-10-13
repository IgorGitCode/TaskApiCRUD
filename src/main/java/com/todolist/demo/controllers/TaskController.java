package com.todolist.demo.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.demo.models.TaskModel;
import com.todolist.demo.services.TaskServices;

@RestController
public class TaskController {
    
    private final TaskServices taskServices;

    @Autowired
    public TaskController(TaskServices taskServices) {
        this.taskServices = taskServices;
    };

    @GetMapping("/")
    public ResponseEntity<List<TaskModel>> getAllTask(){
        List<TaskModel> tasks = taskServices.getAllTasks();
        System.out.println(tasks.toString());
        return ResponseEntity.ok(tasks);
    };

    @PostMapping("/create")
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskModel task1) {
        TaskModel createdTask = taskServices.createTask(task1);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    };

    @PutMapping("/alter")
    public ResponseEntity<TaskModel> updateTask(@RequestBody TaskModel updatedTask) {
        try {
            TaskModel taskToUpdate = taskServices.updateTask(updatedTask.getId(), updatedTask);
            return ResponseEntity.ok(taskToUpdate);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    };

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TaskModel> deleteTask(@PathVariable Long id) {
        try {
            taskServices.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    };
};