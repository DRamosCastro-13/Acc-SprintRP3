package com.example.taskservice.services;

import com.example.taskservice.dtos.TaskDTO;
import com.example.taskservice.models.TaskEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<TaskEntity> getTaskById(Long id);
    Mono<TaskDTO> getTaskDTOById(Long id);
    Flux<TaskEntity> getAllTasks();
    Flux<TaskDTO> getAllTasksDTO();
    Mono<TaskEntity> createTask(TaskEntity task);
    Mono<TaskEntity> updateTask(Long id, TaskEntity task);
    Mono<Void> deleteTask(Long id);
    Mono<TaskEntity> saveTask(TaskEntity task);
}
