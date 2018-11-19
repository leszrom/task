package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    Task save(Task task);

    @Override
    List<Task> findAll();

    Optional<Task> findById(Long id);

    void deleteById(Long id);
}
