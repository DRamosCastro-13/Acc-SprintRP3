package com.example.taskservice;

import com.example.taskservice.controllers.TaskController;
import com.example.taskservice.dtos.NewTaskDTO;
import com.example.taskservice.dtos.TaskDTO;
import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import com.example.taskservice.services.TaskService;
import com.example.taskservice.validator.TaskValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.Errors;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        TaskEntity task = new TaskEntity(1L, "Old Title", "Old description", "Pending", "email@example.com");
        Mockito.when(taskRepository.findById(1L)).thenReturn(Mono.just(task));
    }


    @Test
    public void testUpdateTask() {
        Long taskId = 1L;

        NewTaskDTO updateRequest = new NewTaskDTO("updated title", "updated description", "In progress", "example@email.com");

        // Perform the PUT request and assert response
        webTestClient.put()
                .uri("/api/tasks/" + taskId)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskEntity.class)
                .value(savedTask -> {
                    assertEquals("updated title", savedTask.getTitle());
                    assertEquals("updated description", savedTask.getDescription());
                });
    }
}
