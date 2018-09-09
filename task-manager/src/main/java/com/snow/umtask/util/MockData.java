package com.snow.umtask.util;

import com.alibaba.fastjson.JSON;
import com.snow.umtask.model.EmailPerson;
import com.snow.umtask.pagemodel.EmailListenerPm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockData {

    public static void main(String[] args) {
        EmailListenerPm emailListenerPm = new EmailListenerPm();
        emailListenerPm.setJobClassName("classname");
        emailListenerPm.setJobGroup("group");
        emailListenerPm.setListenerName("listenername");

        Map map = new HashMap<>();
        EmailPerson person1 = new EmailPerson();
        person1.setEmailAddress("1547756164@qq.com");
        person1.setPersonName("snow");
        person1.setUserid(1L);


        List successPerson = new  ArrayList<>();
        successPerson.add(person1);

        List errorPerson = new ArrayList();
        errorPerson.add(person1);

        map.put("success",successPerson);
        map.put("error",errorPerson);


        emailListenerPm.setRecivePersons(map);

       ;

        System.out.println( JSON.toJSONString(emailListenerPm));
    }


}
