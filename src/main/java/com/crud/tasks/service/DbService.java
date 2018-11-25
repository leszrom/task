package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbService {
    private final TaskRepository taskRepository;

    @Autowired
    public DbService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(final Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(final Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTaskById(final Long id) {
        taskRepository.deleteById(id);
    }
}
