package kr.co.ts.springboot;

import kr.co.ts.springboot.web.HelloController;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void helloReturns() throws Exception{
        String msg = "Hello";

        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello"));
    }

    @Test
    public void dto리턴() throws Exception{
        String name = "hello";
        int amount = 1000;
        MvcResult testResult =
        mvc.perform(MockMvcRequestBuilders.get("/hello/dto").param("name",name).param("amount",String.valueOf(amount)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount",Matchers.is(amount)))
                .andReturn();

        System.out.println(testResult.getRequest().getAttributeNames().nextElement());
    }
}
