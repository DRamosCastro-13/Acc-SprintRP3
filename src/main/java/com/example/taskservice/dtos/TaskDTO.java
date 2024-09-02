package com.example.taskservice.dtos;

import com.example.taskservice.models.TaskEntity;

public class TaskDTO {

   private Long id;

    private String title;

    private String description;

    private String status;

    private String email;

    public TaskDTO(){}

    public TaskDTO(TaskEntity taskEntity) {
        id = taskEntity.getId();
        title = taskEntity.getTitle();
        description = taskEntity.getDescription();
        status = taskEntity.getStatus();
        email = taskEntity.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }
}
