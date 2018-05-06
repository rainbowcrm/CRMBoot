package com.rainbow.util;

import com.rainbow.ApplicationManager;
import com.rainbow.crm.database.SpringBootConnectionCreater;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ServiceLibrary implements  IServiceLibrary,ApplicationContextAware {



    private static ApplicationContext applicationContext;


    @Autowired
    ApplicationManager applicationManager;

    @Autowired
    SpringBootConnectionCreater springBootConnectionCreater ;

    public  ApplicationManager getApplicationManager()
   {
       return applicationManager ;
   }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
    public static ServiceLibrary services() {
        return applicationContext.getBean(ServiceLibrary.class);
    }

    @Override
    public SpringBootConnectionCreater getSpringBootConnectionCreator() {
        return springBootConnectionCreater;
    }


}
