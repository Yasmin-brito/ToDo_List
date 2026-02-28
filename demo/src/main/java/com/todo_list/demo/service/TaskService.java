package com.todo_list.demo.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todo_list.demo.dtos.TaskRecordDto;
import com.todo_list.demo.dtos.TaskStatusDto;
import com.todo_list.demo.model.TaskModel;
import com.todo_list.demo.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public ResponseEntity<TaskModel> save(TaskRecordDto taskRecordDto, TaskModel taskModel){
        
        
        BeanUtils.copyProperties(taskRecordDto, taskModel);
        //fazer codigo para evitar duplicacao de descrição
        taskModel.setStatus("PENDENTE");
        taskModel.setCreated_at(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }
    public ResponseEntity<TaskModel> saveStatus(TaskStatusDto taskStatusDto, TaskModel taskModel){
        
        
        taskModel.setStatus(taskStatusDto.status());
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(taskModel));
    }
    public ResponseEntity<TaskModel> saveUpdate(TaskModel taskModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }

    public List<TaskModel> allTasks(){
        return taskRepository.findAll();
    }

    public Optional<TaskModel> findTaskById(UUID id){
        return taskRepository.findById(id);
    }

    public ResponseEntity<Void> deleteTask(TaskModel taskModel){
        taskRepository.delete(taskModel);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
