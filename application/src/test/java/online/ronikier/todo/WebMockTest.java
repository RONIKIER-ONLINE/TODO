package online.ronikier.todo;

import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.interfaces.web.TaskController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(TaskController.class)
public class WebMockTest {

	/*

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService taskService;

	@Test
	public void homeShouldReturnMessageFromService() throws Exception {
		when(taskService.kill()).thenReturn("Lou kills");
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Lou rulez")));
	}

	 */

}
