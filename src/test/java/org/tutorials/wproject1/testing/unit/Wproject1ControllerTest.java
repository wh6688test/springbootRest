package org.tutorials.wproject1.testing.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tutorials.wproject1.testing.JsonUtil;

import org.springframework.http.MediaType;
import org.tutorials.wproject1.controller.Wproject1Controller;
import org.tutorials.wproject1.model.Group;
import org.tutorials.wproject1.model.Member;
import org.tutorials.wproject1.service.GroupService;


import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//between controller and HTTP layer
//@WebMvcTest(value = Wproject1Controller.class, excludeAutoConfiguration= SecurityAutoConfiguration)
@AutoConfigureMockMvc
@WebMvcTest(controllers = Wproject1Controller.class)
public class Wproject1ControllerTest {


	@Autowired
	private MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;

    @MockBean
	private GroupService service;

    @BeforeEach
    public void methodSetUp() throws Exception {

	}

	@Test
	void whenPostGroupThenGroupPosted200() throws Exception {
		Map<String, String> attr=new HashMap<>();
		attr.put("att", "value");
		Group postedGroup=new Group(1L, attr);
		given(service.createGroup(attr)).willReturn(postedGroup);
		mvc.perform(post("/wrest/group").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJsonByte(attr))).andExpect(status().isCreated()).andExpect(jsonPath("$.gid",greaterThan(0)));
		verify(service, VerificationModeFactory.times(1)).createGroup(attr);
		reset(service);
	}

	@Test
	void whenGetAllGroupsThenReturnJsonArray() throws Exception {

		Map<String, String> attr1Map=new HashMap<>();
		attr1Map.put("attr1", "value1");


		Member member1= new Member("2", (short)4);
		Group grp0=new Group(new Long(1));
		Group grp1 = new Group(new Long(2), attr1Map);
		Group grp2 = new Group(new Long(3), attr1Map, member1);

		List<Group> allGroups=new ArrayList<>();
		allGroups.add(grp0);
		allGroups.add(grp1);
		allGroups.add(grp2);

		given(service.findAll()).willReturn(allGroups);
		//todo : add value verification
		mvc.perform(get("/wrest/groups").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(3)))
				.andExpect(jsonPath("$[0].attributes").isEmpty()).andExpect(jsonPath("$[1].attributes").isNotEmpty())
				.andExpect(jsonPath("$[2].members").isNotEmpty())
		        .andExpect(jsonPath("$[2].members", hasKey("2")))
				.andExpect(jsonPath("$[2].members.2.rating", is(4)));

		verify(service, VerificationModeFactory.times(1)).findAll();
		reset(service);
	}

	@Test
	void whenGetSpecificGroupThenReturnTheGroup200() throws Exception {

		Map<String, String> attr1Map=new HashMap<String, String>();
		attr1Map.put("attr1", "value1");
		Member member1= new Member("1", (short)5);
		Group grp0=new Group(1L);
		Group grp1 = new Group(2L, attr1Map);
		Group grp2 = new Group(3L, attr1Map, member1);

		List<Group> allGroups=new ArrayList<>();
		allGroups.add(grp0);
		allGroups.add(grp1);
		allGroups.add(grp2);

		given(service.findGroup(3L)).willReturn(Optional.of(grp2));
		mvc.perform(get("/wrest/group").param("gid", "3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.members", hasKey("1"))).andExpect(jsonPath("$.members.1", hasValue(5))).andExpect(jsonPath("$.attributes", hasKey("attr1"))).andExpect(jsonPath("$.gid", is(3)));
		verify(service, VerificationModeFactory.times(1)).findGroup(3L);
		reset(service);
	}



	@Test
	void whenUpdateGroupMemberThenReturnUpdatedGroup200() throws Exception {

		Map<String, String> attr1Map=new HashMap<>();
		attr1Map.put("attr1", "value1");
		Member member1= new Member("1", (short)2);
		Member member2= new Member("2", (short)3);

		Group grp1 = new Group(new Long(2), attr1Map, member1);
		Group grp2 = new Group(new Long(2), attr1Map, member2);

		Set<Group> allGroups=new HashSet<>();
		allGroups.add(grp1);

		given(service.updateGroupMember(2L, member2)).willReturn(Optional.of(grp2));

		mvc.perform(put("/wrest/group/{gid}/member/rating", 2L).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJsonByte(member2))).andExpect(status().isOk())
				 .andExpect(jsonPath("$.gid", is(2)))
				 .andExpect(jsonPath("$.members", hasKey("2"))).andExpect(jsonPath("$.members.2.rating", is(3)))
				 .andExpect(jsonPath("$.attributes", hasKey("attr1"))).andExpect(jsonPath("$.attributes", hasValue("value1")));

		verify(service, VerificationModeFactory.times(1)).updateGroupMember(2L, member2);
		reset(service);
	}


	@Test
	void whenGetSpecificMemberThenReturnContainingGroups() throws Exception {

		Map<String, String> attr1Map=new HashMap<>();
		attr1Map.put("attr1", "value1");


		Member member1= new Member("1", (short)5);
		Group grp0=new Group(new Long(1));
		Group grp1 = new Group(new Long(2), attr1Map);
		Group grp2 = new Group(new Long(3), attr1Map, member1);

		List<Group> allGroups=new ArrayList<>();
		allGroups.add(grp0);
		allGroups.add(grp1);
		allGroups.add(grp2);

		List<Group>returnedGroups=new ArrayList<>();
		returnedGroups.add(grp2);

		given(service.findMemberById("1")).willReturn(Optional.of(returnedGroups));
		//200OK
		mvc.perform(get("/wrest/group/member/{memberId}", "1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$[0].members", hasKey("1"))).andExpect(jsonPath("$[0].members.1.rating", is(5)));

		//note : updated the controller to return Optional intead of NotFound : means empty result will be returned instead
		mvc.perform(get("/wrest/group/member/{memberId}", "member22").contentType(MediaType.APPLICATION_JSON))
		   //.andDo(print()).andExpect(status().isNotFound());
		   .andDo(print()).andExpect(jsonPath("$").doesNotExist());

		verify(service, VerificationModeFactory.times(1)).findMemberById("1");
		verify(service, VerificationModeFactory.times(1)).findMemberById("member22");
		reset(service);

	}


	@Test
	void whenDeleteGroupReturn204() throws Exception {

		Map<String, String> attr1Map=new HashMap<>();
		attr1Map.put("attr1", "value1");

		Group grp1=new Group(1L, attr1Map);

		List<Group> allGroups=new ArrayList<>();
		allGroups.add(grp1);

		when(service.createGroup(attr1Map)).thenCallRealMethod();
		when(service.findGroup(1L)).thenReturn(Optional.of(grp1));
	    doAnswer(invocation -> {
	    	Object arg0 = invocation.getArgument(0);
	    	assertEquals(grp1, arg0);
	    	assertEquals(1L, grp1.getGid());
	    	return null;

		}).when(service).deleteGroup(grp1);
		mvc.perform(delete("/wrest/group/{gid}", "1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
		verify(service, VerificationModeFactory.times(1)).deleteGroup(grp1);
		reset(service);
	}

}