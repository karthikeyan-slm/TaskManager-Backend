package com.taskmanager;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
 //import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.controller.TaskController;
import com.taskmanager.domain.Task;
import com.taskmanager.service.TaskServiceImpl;


public class TaskControllerTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private TaskServiceImpl tservice;

	private MockMvc mockMvc;

	@InjectMocks
	private TaskController taskcontroller;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(taskcontroller).build();
	}

	@Test
	public void testListAllTask() throws Exception, ParseException {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		LocalDate edate1 = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate sdate1 = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate edate2 = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate sdate2 = LocalDate.of(2017,Month.FEBRUARY,3);
		Task task2 = new Task();
		Task task1 = new Task();
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

		List<Task> types = new ArrayList<Task>();
		types.add(task1);
		types.add(task2);
		when(tservice.findAllTask()).thenReturn(types);
		mockMvc.perform(get("/viewTask")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$[0].taskId", is(1))).andExpect(jsonPath("$[0].task", is("Task1")))
				.andExpect(jsonPath("$[0].priority", is(12))).andExpect(jsonPath("$[0].parentTask", is("parentask1")))
				.andExpect(jsonPath("$[0].startDate", notNullValue()))
				.andExpect(jsonPath("$[0].endDate", notNullValue())).andExpect(jsonPath("$[0].activeTask", is(true)))
				.andExpect(jsonPath("$[1].taskId", is(2))).andExpect(jsonPath("$[1].task", is("Task2")))
				.andExpect(jsonPath("$[1].priority", is(13))).andExpect(jsonPath("$[1].parentTask", is("parentask2")))
				.andExpect(jsonPath("$[1].activeTask", is(false)))

				.andDo(print());
		verify(tservice, times(1)).findAllTask();
		verifyNoMoreInteractions(tservice);
	}
	

	@Test
	public void testPostTask() throws Exception {
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
		
	//	when(tservice.addTask(object)).thenReturn(object);
		mockMvc.perform(
				post("/addTask").contentType(APPLICATION_JSON_UTF8).content(TaskUtil.ObjecttoJSON(object)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.taskId", is(4)))
				.andExpect(jsonPath("$.task", is("Task1"))).andExpect(jsonPath("$.priority", is(12)))
			.andExpect(jsonPath("$.parentTask", is("parentask1")))
			.andExpect(jsonPath("$.startDate", notNullValue())).andExpect(jsonPath("$.endDate", notNullValue()))
			.andExpect(jsonPath("$.activeTask", is(true))).andDo(print());
	}

	@Test
	public void testPostTaskExceptin() throws Exception {
		Task object = new Task();
		object.setTaskId(4);
		object.setTask("Task1");
		object.setPriority(12);
		object.setParentTask("parentask1");
		object.setStartDate(null);
		object.setEndDate(null);
		
	//	when(tservice.viewAllTask(object)).thenThrow(new NoValuesFoundException());
		mockMvc.perform(post("/api/task/create").contentType(APPLICATION_JSON_UTF8).content(asJsonString(object)))
				.andExpect(status().isBadRequest()).andDo(print());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testUpdateTask() throws Exception {
		SimpleDateFormat dformat = new SimpleDateFormat("MM-dd-yyyy");
		LocalDate sdate1 = LocalDate.of(2017,Month.FEBRUARY,3);
		LocalDate edate1 = LocalDate.of(2017,Month.FEBRUARY,3);

		Task task1 = new Task();
		Task task2 = new Task();
		task1.setTaskId(1);
		task1.setTask("Task1");
		
		task1.setPriority(12);
		task1.setParentTask("parentask1");
		task1.setStartDate(edate1);
		task1.setEndDate(sdate1);
	
		task2.setTaskId(1);
		task2.setTask("Task1");
		task2.setPriority(13);
		task2.setParentTask("parentask1");
		task2.setStartDate(edate1);
		task2.setEndDate(sdate1);
	
		
	}

	

}
