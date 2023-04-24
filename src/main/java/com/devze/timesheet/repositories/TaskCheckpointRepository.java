package com.devze.timesheet.repositories;

import com.devze.timesheet.entities.Task;
import com.devze.timesheet.entities.TaskCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskCheckpointRepository extends JpaRepository<TaskCheckpoint, Long> {

    Optional<TaskCheckpoint> findOptionalByTaskAndFinishedAtNull(Task task);
}
