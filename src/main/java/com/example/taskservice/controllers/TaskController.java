package com.example.taskservice.controllers;

import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Service API", description = "API for managing requests related to Tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public Mono<TaskEntity> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @GetMapping
    public Flux<TaskEntity> getAllTasks(){
        return taskService.getAllTasks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TaskEntity> createTask(@RequestBody TaskEntity task){ //validar body
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Mono<TaskEntity> updateTask(@PathVariable Long id, @RequestBody TaskEntity task){
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
}
