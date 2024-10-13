package com.todolist.demo.repositories;
import com.todolist.demo.models.TaskModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {

};