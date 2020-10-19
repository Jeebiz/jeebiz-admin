package com.xxl.job.admin.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.xxl.job.admin.service.LoginService;

public class JobInfoControllerTest extends AbstractSpringMvcTest {

  private Cookie cookie;

  @Before
  public void login() throws Exception {
    MvcResult ret = mockMvc.perform(
        post("/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("userName", "admin")
            .param("password", "123456")
    ).andReturn();
    cookie = ret.getResponse().getCookie(LoginService.LOGIN_IDENTITY_KEY);
  }

  @Test
  public void testAdd() throws Exception {
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
    parameters.add("jobGroup", "1");

    MvcResult ret = mockMvc.perform(
        post("/jobinfo/pageList")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            //.content(paramsJson)
            .params(parameters)
            .cookie(cookie)
    ).andReturn();

    System.out.println(ret.getResponse().getContentAsString());
  }

}
