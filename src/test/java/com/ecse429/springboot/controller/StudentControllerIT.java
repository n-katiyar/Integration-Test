package com.ecse429.springboot.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecse429.springboot.StudentServicesApplication;
import com.ecse429.springboot.model.Course;
import com.ecse429.springboot.service.StudentService;

//@RunWith(SpringRunner.class)
//@WebMvcTest(value = StudentController.class)
//@WithMockUser
//public class StudentControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private StudentService studentService;
//
//	Course mockCourse = new Course("Course5", "Spring", "10Steps",
//			Arrays.asList("Learn Maven", "Import Project", "First Example",
//					"Second Example"));
//
//	String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";
//

//	@Test
//	public void createStudentCourse() throws Exception {
//		Course mockCourse = new Course("5", "Final Number", "5",
//				Arrays.asList("1", "2", "3", "4"));
//
//		// studentService.addCourse to respond back with mockCourse
//		Mockito.when(
//				studentService.addCourse(Mockito.anyString(),
//						Mockito.any(Course.class))).thenReturn(mockCourse);
//
//		// Send course as body to /students/Student1/courses
//		RequestBuilder requestBuilder = MockMvcRequestBuilders
//				.post("/students/Student1/courses")
//				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
//				.contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		MockHttpServletResponse response = result.getResponse();
//
//		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//
//		assertEquals("http://localhost/students/Student1/courses/5",
//				response.getHeader(HttpHeaders.LOCATION));
//
//	}

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentServicesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testRetrieveStudentCourse() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/students/Student1/courses/Course1"),
				HttpMethod.GET, entity, String.class);

		String expected = "{\"id\":\"Course 1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";
		System.out.println(response);
		System.out.println(expected);

		try {
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			System.out.println("Not matched");
		}
		
		//JSONAssert.assertEquals(expected, response.getBody(), false);

	}

//	@Test
//	public void addCourse() {
//
//		Course course = new Course("Course6", "Spring6", "10Steps",
//				Arrays.asList("Learn Maven", "Import Project", "First Example",
//						"Second Example"));
//
//		HttpEntity<Course> entity = new HttpEntity<Course>(course, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(
//				createURLWithPort("/students/Student1/courses/"),
//				HttpMethod.POST, entity, String.class);
//
//		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
//
//		assertTrue(actual.contains("/students/Student1/courses/"));
//		System.out.println(actual);
//
//	}
//
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
