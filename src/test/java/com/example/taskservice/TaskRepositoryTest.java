package com.example.taskservice;

import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.scheduling.config.Task;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataR2dbcTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void shouldFindTaskById() {
        TaskEntity task = new TaskEntity("Title", "Description", "Status", "Email");
        taskRepository.save(task).block();

        taskRepository.findById(task.getId())
                .as(StepVerifier::create)
                .expectNextMatches(foundTask -> "Title".equals(foundTask.getTitle()))
                .verifyComplete();
    }

    @Test
    public void shouldUpdateTask(){
        Long taskId = 1L;
        TaskEntity existingTask = new TaskEntity(taskId, "Old Title", "Old description", "Pending", "email@example.com");
        String expectedTitle = "Updated Title";
        String expectedDescription = "Updated description";
        String expectedStatus = "In Progress";

        existingTask.setTitle(expectedTitle);
        existingTask.setDescription(expectedDescription);
        existingTask.setStatus(expectedStatus);

        // Mock taskRepository.save behavior
        Mockito.when(taskRepository.save(existingTask)).thenReturn(Mono.just(existingTask));

        // Perform update operation using StepVerifier
        taskRepository.findById(taskId)
                .as(StepVerifier::create)
                .expectNextMatches(updatedTask -> {
                    assertEquals(expectedTitle, updatedTask.getTitle());
                    assertEquals(expectedDescription, updatedTask.getDescription());
                    assertEquals(expectedStatus, updatedTask.getStatus());
                    return true;
                })
                .verifyComplete();

    }
}
