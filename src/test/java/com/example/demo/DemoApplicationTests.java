package com.example.demo;

import com.example.demo.chain.Chain;
import com.example.demo.chain.ChainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private ChainService chainService;

    /**
     * Placeholder{name='haha', description='haha is a ....', title='goat 358a27ed-b9de-4084-9436-90591122af10', age=624438463}
     */
    @Test
    public void contextLoads() {
        Chain chain = chainService.setService();
        System.out.println(chain);
    }

    @Autowired private ApplicationContext ac;

    @Test
    public void test0() {
        String[] str= ac.getBeanDefinitionNames();
        for (String string : str) {
            System.out.println("***---***"+string);
        }
    }

    @Test
    public void test1() {
        ChainService chainService = ac.getBean("chainService",ChainService.class);
        System.out.println(chainService);
    }
}
