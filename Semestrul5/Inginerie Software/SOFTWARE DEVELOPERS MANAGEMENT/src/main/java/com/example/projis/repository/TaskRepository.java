package com.example.projis.repository;

import com.example.projis.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task,Integer> {
}
