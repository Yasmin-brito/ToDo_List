package com.todo_list.demo.dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskRecordDto(@NotBlank String description) {
}
