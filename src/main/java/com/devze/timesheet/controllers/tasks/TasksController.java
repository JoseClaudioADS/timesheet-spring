package com.devze.timesheet.controllers.tasks;

import com.devze.timesheet.controllers.tasks.dto.*;
import com.devze.timesheet.entities.Task;
import com.devze.timesheet.repositories.TaskRepository;
import com.devze.timesheet.services.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasks")
public class TasksController {

    private TaskRepository taskRepository;
    private TaskService taskService;

    public TasksController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity newTask(@RequestBody @Valid CreateTaskDTO createTaskDTO) {
        var newTask = createTaskDTO.toTask();
        this.taskRepository.save(newTask);
        return ResponseEntity.ok(newTask.getId());
    }

    @GetMapping
    public ResponseEntity<ListTasksDTO> listTasks() {
        var tasks = this.taskRepository.findAll();
        var mappedTasks = tasks.stream().map(task -> {
            return new TaskDTO(task.getId(), task.getName(), task.getCreatedAt());
        }).collect(Collectors.toList());
        var result = new ListTasksDTO(mappedTasks);
        return ResponseEntity.ok(result);
    }

    @PostMapping("{id}/checkpoint")
    public ResponseEntity checkpoint(@PathVariable("id") Long taskId) {
        var taskToCheckpoint = this.taskRepository.findById(taskId);
        if (taskToCheckpoint.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        this.taskService.checkpoint(taskToCheckpoint.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable("id") Long taskId) {
        var taskDb = this.taskRepository.findById(taskId);
        if (taskDb.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        this.taskRepository.delete(taskDb.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<DetailTaskDTO> detail(@PathVariable("id") Long taskId) {
        var taskDb = this.taskRepository.findById(taskId);
        if (taskDb.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var taskCheckpointsDTO = taskDb.get().getCheckpoints().stream().map(taskCheckpoint -> {
            return new TaskCheckpointDTO(taskCheckpoint.getStartedAt(), taskCheckpoint.getFinishedAt());
        }).sorted(Comparator.comparing(TaskCheckpointDTO::startedAt)).collect(Collectors.toList());

        return ResponseEntity.ok(new DetailTaskDTO(TaskDTO.from(taskDb.get()),
                taskCheckpointsDTO));
    }
}
