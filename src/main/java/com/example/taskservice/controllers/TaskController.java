package com.example.taskservice.controllers;

import com.example.taskservice.dtos.NewTaskDTO;
import com.example.taskservice.dtos.TaskDTO;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<TaskDTO> getTaskById(@PathVariable Long id){
        return taskService.getTaskDTOById(id);
    }

    @GetMapping
    public Flux<TaskDTO> getAllTasks(){
        return taskService.getAllTasksDTO();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TaskEntity> createTask(@RequestBody NewTaskDTO task){ //validar body
        TaskEntity newTask = new TaskEntity(task.title(), task.description(), task.status(), task.email());
        return taskService.createTask(newTask);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TaskEntity> updateTask(@PathVariable Long id, @RequestBody NewTaskDTO task){
        TaskEntity updateTask = new TaskEntity(task.title(), task.description(), task.status(), task.email());
        return taskService.updateTask(id, updateTask);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
}
