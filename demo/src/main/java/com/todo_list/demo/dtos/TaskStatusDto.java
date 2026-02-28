package com.todo_list.demo.dtos;

import jakarta.validation.constraints.NotNull;

public record TaskStatusDto(@NotNull String status){

}