package com.company;

import com.company.service.TodoService;
import com.company.service.UserService;
import com.company.ui.AppUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationConfig.xml");
        AppUI bean = context.getBean(AppUI.class);
        context.getBean(UserService.class);
        context.getBean(TodoService.class);
        bean.run();
    }
}
