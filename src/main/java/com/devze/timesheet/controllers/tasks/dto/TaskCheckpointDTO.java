package com.devze.timesheet.controllers.tasks.dto;

import java.time.Instant;

public record TaskCheckpointDTO(Instant startedAt, Instant finishedAt) {
}
