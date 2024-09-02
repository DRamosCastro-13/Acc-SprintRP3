package com.example.taskservice.validator;

import com.example.taskservice.dtos.NewTaskDTO;
import com.example.taskservice.models.TaskEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;

@Component
public class TaskValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        if (target instanceof NewTaskDTO) {
            NewTaskDTO taskDTO = (NewTaskDTO) target;

            if (!isValidTask(taskDTO, errors)) {
                errors.reject("task.invalid", "Invalid task. Please check all fields.");
            }
        }

        TaskEntity task = (TaskEntity) target;

        // Validate task title
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            errors.rejectValue("title", "task.title.empty", "Task title cannot be empty");
        }

        // Validate task description
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            errors.rejectValue("description", "task.description.empty", "Task description cannot be empty");
        }

        // Validate task status
        if (!isValidStatus(task.getStatus())) {
            errors.rejectValue("status", "task.status.invalid", "Invalid status. Must be 'Pending', 'In progress', or 'Completed'");
        }

    }

    private boolean isValidStatus(String status) {
        return "Pending".equals(status) || "In progress".equals(status) || "Completed".equals(status);
    }

    private boolean isValidTask(NewTaskDTO taskDTO, Errors errors) {
        boolean valid = true;

        if (taskDTO.title().isBlank()) {
            errors.rejectValue("title", "task.title.empty", "Task title cannot be empty");
            valid = false;
        }

        if (taskDTO.description().isBlank()) {
            errors.rejectValue("description", "task.description.empty", "Task description cannot be empty");
            valid = false;
        }

        if (taskDTO.email().isBlank()) {
            errors.rejectValue("email", "task.email.empty", "Task email cannot be empty");
            valid = false;
        }

        if (!isValidStatus(taskDTO.status())) {
            errors.rejectValue("status", "task.status.invalid", "Invalid status. Must be 'Pending', 'In progress', or 'Completed'");
            valid = false;
        }

        return valid;
    }
}
