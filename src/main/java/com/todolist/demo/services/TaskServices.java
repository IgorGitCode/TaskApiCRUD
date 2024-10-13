package com.todolist.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.todolist.demo.models.TaskModel;
import com.todolist.demo.repositories.TaskRepository;

@Service
public class TaskServices {
    
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServices(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    };

    public List<TaskModel> getAllTasks() {
        return taskRepository.findAll();
    };

    public TaskModel createTask(TaskModel taskToCreate) {
        return taskRepository.save(taskToCreate);
    };

    public TaskModel updateTask(Long id, TaskModel taskToUpdate) {
        Optional<TaskModel> optionalTask = taskRepository.findById(id);

        if(optionalTask.isPresent()) {
            TaskModel taskNewValues = optionalTask.get();
            BeanUtils.copyProperties(taskToUpdate, taskNewValues, "id");

            return taskRepository.save(taskNewValues);
        } else {
            throw new RuntimeException("task not found by id: " + id);
        }
    };

    public void deleteTask(Long id) {
        if(taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found By id: " + id);
        }
    };
};