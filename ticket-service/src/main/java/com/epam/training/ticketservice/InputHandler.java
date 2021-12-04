package com.epam.training.ticketservice;

import org.springframework.stereotype.Component;


@Component
public class InputHandler{

    public void handleCommand(String input){
        String[] components = input.split("\"");
        for(String component : components){
            System.out.println(component);
        }
    }
}
