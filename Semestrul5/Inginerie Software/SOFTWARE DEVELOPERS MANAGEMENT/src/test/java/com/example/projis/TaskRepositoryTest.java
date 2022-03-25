package com.example.projis;

import com.example.projis.model.Task;
import com.example.projis.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TaskRepositoryTest {
    @Autowired private TaskRepository repo;

    @Test
    public void testAddNew() {
        Task task = new Task();
        task.setIdUser(2);
        task.setTaskName("Implementarea unei case inteligente cu arduino.");

        Task savedTask = repo.save(task);

        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<Task> tasks = repo.findAll();
        Assertions.assertThat(tasks).hasSize(2);
        for (Task task: tasks ) {
            System.out.println(task);
        }
    }
}
