package com.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.domain.Task;
import com.taskmanager.exception.RecordNotFoundException;
import com.taskmanager.repository.TaskRepository;


@Service
public class TaskServiceImpl {
	
	@Autowired
	private TaskRepository taskRepo;

	public Task addTask(Task task) {
		Task t = taskRepo.save(task);
		return t;
	}

	public Task updateTask(Task task) {
		Task t = taskRepo.save(task);
		return t;
	}

	public Task findTaskById(long task_id) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		Optional<Task> task = taskRepo.findById(task_id);
		 if(task.isPresent()) {
	            return task.get();
	        } else {
	            throw new RecordNotFoundException("No employee record exist for given id");
	        }
	}

	public List<Task> findAllTask() {
		return (List<Task>) taskRepo.findAll();

	}
	
	public void delete(Task task) {
		taskRepo.delete(task);
	}

}
