package com.seaker.seaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;

@Controller
public class ApplicationController {


    private Parser parser;

    @Autowired
    public ApplicationController(Parser parser) {
        this.parser = parser;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void contextRefreshedEvent() throws UnsupportedEncodingException {
//        collectForCity("2", "Saint Petersburg");
//        collectForCity("1", "Moscow");
//        collectForCity("10", "Volgograd");
//        collectForCity("37", "Vladivostok");
//        collectForCity("153", "Khabarovsk");
//        collectForCity("49", "Yekaterinburg");
//        collectForCity("60", "Kazan");
//        collectForCity("61", "Kaliningrad");
//        collectForCity("72", "Krasnodar");
//        collectForCity("73", "Krasnoyarsk");
//        collectForCity("95", "Nizhny Novgorod");
//        collectForCity("99", "Novosibirsk");
//        collectForCity("104", "Omsk");
//        collectForCity("110", "Perm");
//        collectForCity("119", "Rostov-on-Don");
//        collectForCity("123", "Samara");
//        collectForCity("151", "Ufa");
//        collectForCity("158", "Chelyabinsk");
        parser.collectForCity("21", "Армавир", 16, 50);
    }
}
