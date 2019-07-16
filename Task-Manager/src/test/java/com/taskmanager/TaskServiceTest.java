package com.taskmanager;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taskmanager.domain.Task;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.service.TaskServiceImpl;




public class TaskServiceTest {
	
	@InjectMocks
	TaskServiceImpl tservice;

	@Mock
	TaskRepository repos;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllTaskTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		LocalDate sdate1 = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate edate1 = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate sdate2 = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate edate2 = LocalDate.of(2017,Month.FEBRUARY,3);
		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTaskId(1);
		task1.setTask("Task1");
		task1.setPriority(12);
		task1.setParentTask("parentask1");
		task1.setStartDate(edate1);
		task1.setEndDate(sdate1);
		
		task2.setTaskId(2);
		task2.setTask("Task2");
		task2.setPriority(13);
		task2.setParentTask("parentask2");
		task2.setStartDate(edate2);
		task2.setEndDate(sdate2);
	
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task1);
		tasks.add(task2);
		when(repos.findAll()).thenReturn(tasks);
		// test
		List<Task> taskList = tservice.findAllTask();
		assertEquals(2, taskList.size());
		verify(repos, times(1)).findAll();

	}

	@Test
	public void createTaskTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		LocalDate sdate = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate edate = LocalDate.of(2017,Month.FEBRUARY,3);
		Task object = new Task();
		object.setTaskId(4);
		object.setTask("Task1");
		object.setPriority(12);
		object.setParentTask("parentask1");
		object.setStartDate(edate);
		object.setEndDate(sdate);
		
		tservice.addTask(object);

		verify(repos, times(1)).save(object);
	}

	@Test
	public void updateTaskTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		LocalDate sdate = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate edate = LocalDate.of(2017,Month.FEBRUARY,3);
		Task object = new Task();
		object.setTaskId(4);
		object.setTask("Task1");
		object.setPriority(12);
		object.setParentTask("parentask1");
		object.setStartDate(edate);
		object.setEndDate(sdate);
		
		tservice.updateTask(object);

		verify(repos, times(1)).save(object);
	}

	@Test
	public void getTaskByIdTest() throws ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		LocalDate sdate = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate edate = LocalDate.of(2017,Month.FEBRUARY,3);
		Task object = new Task();
		object.setTaskId(4);
		object.setTask("Task1");
		object.setPriority(12);
		object.setParentTask("parentask1");
		object.setStartDate(edate);
		object.setEndDate(sdate);
		
		
	}

	
	

}
