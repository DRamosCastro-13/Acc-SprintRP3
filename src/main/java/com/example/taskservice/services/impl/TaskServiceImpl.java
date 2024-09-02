package com.example.taskservice.services.impl;

import com.example.taskservice.dtos.TaskDTO;
import com.example.taskservice.handlers.TaskNotFoundException;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import com.example.taskservice.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Mono<TaskEntity> getTaskById(Long id) {
        return taskRepository.findById(id).switchIfEmpty( Mono.error( new TaskNotFoundException("Task with ID: " + id + " not found")) );
    }

    @Override
    public Mono<TaskDTO> getTaskDTOById(Long id) {
        return getTaskById(id).flatMap(taskEntity -> Mono.just(new TaskDTO(taskEntity)));
    }

    @Override
    public Flux<TaskEntity> getAllTasks() {
        return taskRepository.findAll().switchIfEmpty(Mono.error(new TaskNotFoundException("No existing tasks")));
    }

    @Override
    public Flux<TaskDTO> getAllTasksDTO() {
        return getAllTasks().flatMap(taskEntity -> Flux.just(new TaskDTO(taskEntity)));
    }

    @Override
    public Mono<TaskEntity> createTask(TaskEntity task) {
        return taskRepository.save(task);
    }

    @Override
    public Mono<TaskEntity> updateTask(Long id, TaskEntity task) {
        return taskRepository.findById(id).switchIfEmpty(Mono.error(new TaskNotFoundException("Task with ID: " + id + " not found")))
                .flatMap(existingTask -> {
                    existingTask.setTitle(task.getTitle());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setStatus(task.getStatus());
                    return taskRepository.save(existingTask);
                });
    }

    @Override
    public Mono<Void> deleteTask(Long id) {
        return taskRepository.findById(id).switchIfEmpty(Mono.error( new TaskNotFoundException("Task not found for ID: " + id) ))
                .flatMap(taskRepository::delete);
    }

    @Override
    public Mono<TaskEntity> saveTask(TaskEntity task) {
        return taskRepository.save(task);
    }
}
