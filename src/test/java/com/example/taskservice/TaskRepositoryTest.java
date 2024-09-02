package com.example.taskservice;

import com.example.taskservice.models.TaskEntity;
import com.example.taskservice.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

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
}
