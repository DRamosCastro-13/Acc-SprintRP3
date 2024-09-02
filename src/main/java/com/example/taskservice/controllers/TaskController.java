package com.example.taskservice.controllers;

import com.example.taskservice.dtos.NewTaskDTO;
import com.example.taskservice.dtos.TaskDTO;
import com.example.taskservice.handlers.InvalidTaskException;
import com.example.taskservice.handlers.TaskNotFoundException;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.services.TaskService;
import com.example.taskservice.validator.TaskValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Service API", description = "API for managing requests related to Tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /*@Autowired
    private TaskValidator taskValidator;*/

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
    public Mono<ResponseEntity<TaskEntity>> createTask(@Valid @RequestBody Mono<NewTaskDTO> newTask){
        return newTask.flatMap(task -> {
           /* BeanPropertyBindingResult errors = new BeanPropertyBindingResult(task, "task");
            taskValidator.validate(task, errors);*/

            /*if(errors.hasErrors()){
                return Mono.error(new InvalidTaskException(errors));
            }*/

            TaskEntity newTaskEntity = new TaskEntity(task.title(), task.description(), task.status(), task.email());
            return taskService.createTask(newTaskEntity);
        })
        .map(savedTask -> ResponseEntity.ok(savedTask));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TaskEntity> updateTask(@PathVariable Long id, @RequestBody NewTaskDTO task){
        return taskService.getTaskById(id)
                .flatMap(updatedTask -> {
                   /* BeanPropertyBindingResult errors = new BeanPropertyBindingResult(task, "task");
                    taskValidator.validate(updatedTask, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new InvalidTaskException(errors));
                    }*/

                    updatedTask.setTitle(task.title());
                    updatedTask.setDescription(task.description());
                    updatedTask.setStatus(task.status());
                    updatedTask.setEmail(task.email());

                    return taskService.saveTask(updatedTask);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
}

