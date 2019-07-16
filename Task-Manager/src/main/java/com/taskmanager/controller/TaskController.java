package com.taskmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanager.domain.Task;
import com.taskmanager.exception.RecordNotFoundException;
import com.taskmanager.service.TaskServiceImpl;

@RestController

public class TaskController {

@Autowired
private TaskServiceImpl taskImpl;

@PostMapping
public ResponseEntity<String> create(@RequestBody Task task) {
	ResponseEntity<String> resp = null;
	if (task == null) {
		resp = new ResponseEntity<>("No Task found", HttpStatus.NO_CONTENT);
	} else {
		taskImpl.addTask(task);
		resp = new ResponseEntity<>("Task details added successfully!!!" + task, HttpStatus.CREATED);
		System.out.println("Task details added succesfully!!!");
	}
	return resp;

}


@GetMapping("/task/{id}")
public ResponseEntity<?> getEmp(@PathVariable("id") Long task_id) throws RecordNotFoundException {
	Task taskObj = taskImpl.findTaskById(task_id);
	ResponseEntity<?> resp = null;
	if (taskObj == null) {
		resp = new ResponseEntity<>("Task with id " + task_id + " not found", HttpStatus.NOT_FOUND);
	} else {
		resp = new ResponseEntity<>("Fetched Task details for task id " + task_id + " successfully!!! " + taskObj,
				HttpStatus.OK);
	}
	return resp;
}
@GetMapping
public ResponseEntity<List<Task>> findAllTask() {
	return new ResponseEntity<>(taskImpl.findAllTask(), HttpStatus.OK);
}



@PutMapping("/task/{id}")
public ResponseEntity<String> updateTask(@PathVariable(value = "id") Long task_id, @Valid @RequestBody Task task) throws RecordNotFoundException {
	Task taskObj = taskImpl.findTaskById(task_id);
	ResponseEntity<String> resp = null;
	if (taskObj == null) {
		resp = new ResponseEntity<>("Task with id " + task_id + " not found", HttpStatus.NOT_FOUND);
	} else {
		taskObj.setTask(task.getTask());
		taskObj.setPriority(task.getPriority());
		taskObj.setParentTask(task.getParentTask());
		taskObj.setStartDate(task.getStartDate());
		taskObj.setEndDate(task.getEndDate());
		
		Task updateTask = taskImpl.updateTask(task);
		resp = new ResponseEntity<>("Task Updated successfully!!!" + updateTask, HttpStatus.OK);
	}
	return resp;

}
/* DELETE a Task */
@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long taskId) throws RecordNotFoundException {
	Task taskObj = taskImpl.findTaskById(taskId);
	ResponseEntity<?> resp = null;
	if (taskObj== null) {
		resp = new ResponseEntity<>("Task with id " + taskId + " not found", HttpStatus.NOT_FOUND);
	} else {
		taskImpl.delete(taskObj);
		resp = new ResponseEntity<>("Task deleted successfully!!!", HttpStatus.OK);
	}
	return resp;
}


}
