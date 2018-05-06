package com.rainbow.util;


import com.rainbow.crm.database.SpringBootConnectionCreater;

public interface IServiceLibrary {

    public SpringBootConnectionCreater getSpringBootConnectionCreator();

}
