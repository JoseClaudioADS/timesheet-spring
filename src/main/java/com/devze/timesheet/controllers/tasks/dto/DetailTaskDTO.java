package com.devze.timesheet.controllers.tasks.dto;

import com.devze.timesheet.entities.Task;

import java.util.List;

public record DetailTaskDTO(TaskDTO task, List<TaskCheckpointDTO> taskCheckpoints) {
}
