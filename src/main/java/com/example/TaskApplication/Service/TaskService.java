package com.example.TaskApplication.Service;

import com.example.TaskApplication.Modal.Task;
import com.example.TaskApplication.Modal.User;
import com.example.TaskApplication.repository.TaskRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  TaskRepo repo;

  public TaskService(TaskRepo repo) {
    this.repo = repo;
  }

  public List<Task> getTasks() {
    return repo.findAll();
  }

  public Task addtask(Task task) {
    return repo.save(task);
  }

  public Task getTaskByNumber(Integer taskNumber) {
    return repo.findByTaskNumber(taskNumber);
  }

  public Task updatetask(Task task) {
    return repo.save(task);
  }

  public void deleteTask(Integer taskNumber) {
    repo.deleteById(taskNumber);
  }

}