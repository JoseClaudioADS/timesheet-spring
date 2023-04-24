CREATE TABLE task_checkpoints (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   started_at TIMESTAMP NOT NULL,
   finished_at TIMESTAMP,
   task_id INT NOT NULL,
   FOREIGN KEY (task_id) references tasks(id)
);