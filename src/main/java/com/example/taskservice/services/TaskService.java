package com.example.taskservice.services;

import com.example.taskservice.models.TaskEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<TaskEntity> getTaskById(Long id);
    Flux<TaskEntity> getAllTasks();
    Mono<TaskEntity> createTask(TaskEntity task);
    Mono<TaskEntity> updateTask(Long id, TaskEntity task);
    Mono<Void> deleteTask(Long id);
}
