package com.devze.timesheet.controllers.tasks.dto;

import com.devze.timesheet.entities.Task;

import java.time.Instant;

public record TaskDTO(Long id, String name, Instant createdAt) {

    public static TaskDTO from(Task task) {
        return new TaskDTO(task.getId(), task.getName(), task.getCreatedAt());
    }
}
