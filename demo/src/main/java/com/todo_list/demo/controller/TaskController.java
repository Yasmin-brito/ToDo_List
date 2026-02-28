package com.todo_list.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo_list.demo.dtos.TaskRecordDto;
import com.todo_list.demo.dtos.TaskStatusDto;
import com.todo_list.demo.model.TaskModel;
import com.todo_list.demo.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskModel> saveTask(@RequestBody @Valid TaskRecordDto taskRecordDto) {
        TaskModel taskModel = new TaskModel();
        return taskService.save(taskRecordDto, taskModel);
    }
    
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskModel>> getAllTask() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.allTasks());
    }
    
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value="id") UUID id) {
        Optional<TaskModel> taskO = taskService.findTaskById(id);
        if(taskO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskO.get());
    }
    
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value="id") UUID id, @RequestBody @Valid TaskRecordDto taskRecordDto) {
        Optional<TaskModel> taskO = taskService.findTaskById(id);
        if(taskO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        var taskModel = taskO.get();
        BeanUtils.copyProperties(taskRecordDto, taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.save(taskRecordDto, taskModel));
    }

    @PatchMapping("/tasks/{id}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable(value="id") UUID id, @RequestBody @Valid TaskStatusDto taskStatusDto){
        Optional<TaskModel> taskO = taskService.findTaskById(id);
        if (taskO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        var taskModel = taskO.get();
        BeanUtils.copyProperties(taskStatusDto, taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(taskService.saveStatus(taskStatusDto, taskModel));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value="id") UUID id){
        Optional<TaskModel> taskO = taskService.findTaskById(id);
        if (taskO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTask(taskO.get()));
    }
    
}
