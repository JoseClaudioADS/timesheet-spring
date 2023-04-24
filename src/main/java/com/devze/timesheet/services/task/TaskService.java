package com.devze.timesheet.services.task;

import com.devze.timesheet.entities.Task;
import com.devze.timesheet.entities.TaskCheckpoint;
import com.devze.timesheet.repositories.TaskCheckpointRepository;
import com.devze.timesheet.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private TaskCheckpointRepository taskCheckpointRepository;

    public TaskService(TaskRepository taskRepository, TaskCheckpointRepository taskCheckpointRepository) {
        this.taskRepository = taskRepository;
        this.taskCheckpointRepository = taskCheckpointRepository;
    }

    @Transactional
    public void checkpoint(Task task) {

        var taskCheckpointStarted = this.taskCheckpointRepository.findOptionalByTaskAndFinishedAtNull(task);

        if (taskCheckpointStarted.isEmpty()) {
            this.taskCheckpointRepository.save(new TaskCheckpoint(Instant.now(), task));
        } else {
            var taskCheckpointStartedDb = taskCheckpointStarted.get();
            taskCheckpointStartedDb.setFinishedAt(Instant.now());
            this.taskCheckpointRepository.save(taskCheckpointStartedDb);
        }
    }
}
