package com.example.taskservice.dtos;

public class TaskDTO {

   private Long id;

    private String title;

    private String description;

    private String status;

    private String email;

    public TaskDTO(){}

    public TaskDTO(Long id, String title, String description, String status, String email) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.email = email;
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
