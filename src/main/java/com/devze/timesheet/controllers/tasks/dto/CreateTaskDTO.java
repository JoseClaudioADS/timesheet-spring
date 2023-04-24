package com.devze.timesheet.controllers.tasks.dto;

import com.devze.timesheet.entities.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CreateTaskDTO(@NotBlank @Size(min = 3, max = 100) String name) {

    public Task toTask() {
        var newTask = new Task();
        newTask.setName(this.name);
        newTask.setCreatedAt(Instant.now());
        return newTask;
    }
}
