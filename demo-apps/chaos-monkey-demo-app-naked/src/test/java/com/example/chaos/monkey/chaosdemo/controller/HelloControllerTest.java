/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.chaos.monkey.chaosdemo.controller;

import com.example.chaos.monkey.chaosdemo.service.GreetingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Benjamin Wilms
 */

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService greetingServiceMock;
    private String responseService;
    private String responseRepo;

    @Before
    public void setup() {
        responseService = "Hello from service!";
        when(greetingServiceMock.greet()).thenReturn(responseService);
        responseRepo = "Hello from repo!";
        when(greetingServiceMock.greetFromRepo()).thenReturn(responseRepo);
    }


    @Test
    public void shouldReturnHello() throws Exception {


        this.mockMvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(content().string(is("Hello!")));
    }

    @Test
    public void callMockServiceGreet() throws Exception {


        this.mockMvc.perform(get("/greet")).andExpect(status().isOk())
                .andExpect(content().string(is(responseService)));
    }


    @Test
    public void callMockServiceDbGreet() throws Exception {


        this.mockMvc.perform(get("/dbgreet")).andExpect(status().isOk())
                .andExpect(content().string(is(responseRepo)));
    }

    public void shouldReturnGoodbye() throws Exception {
        this.mockMvc.perform(get("/goodbye")).andExpect(status().isOk())
                .andExpect(content().string(is("Goodbye!")));
    }

}