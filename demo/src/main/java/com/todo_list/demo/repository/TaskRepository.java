package com.todo_list.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo_list.demo.model.TaskModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    
}
