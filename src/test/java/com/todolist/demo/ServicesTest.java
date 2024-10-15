package com.todolist.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.todolist.demo.models.TaskModel;
import com.todolist.demo.repositories.TaskRepository;
import com.todolist.demo.services.TaskServices;

@ExtendWith(MockitoExtension.class)  // Habilita o suporte ao Mockito
public class ServicesTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServices taskServices;

    @Test
    public void testCriarTask() {
        TaskModel task = new TaskModel();
        task.setTaskName("Ler um livro");

        when(taskRepository.save(any(TaskModel.class))).thenReturn(task);

        TaskModel criado = taskServices.createTask(task);

        assertEquals("Ler um livro", criado.getTaskName());
    }

    @Test
    public void testBuscarTodasTasks() {
        TaskModel task1 = new TaskModel();
        task1.setTaskName("Estudar Mockito");

        TaskModel task2 = new TaskModel();
        task2.setTaskName("Fazer exercícios");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<TaskModel> tasks = taskServices.getAllTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    public void testAtualizarTask() {
        TaskModel task = new TaskModel();
        task.setId(1L);
        task.setTaskName("Tarefa Antiga");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(TaskModel.class))).thenReturn(task);

        TaskModel atualizada = new TaskModel();
        atualizada.setTaskName("Tarefa Atualizada");

        TaskModel resultado = taskServices.updateTask(1L, atualizada);

        assertEquals("Tarefa Atualizada", resultado.getTaskName());
    }
}
