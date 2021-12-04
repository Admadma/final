package com.epam.training.ticketservice;

import com.epam.training.ticketservice.movies.Movie;
import com.epam.training.ticketservice.movies.MovieController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputReader implements CommandLineRunner {

    @Autowired
    MovieController movieController;

    @Autowired
    InputInterpreter inputInterpreter;

    public void interpretString(String line){
        String[] lineComponents = splitByQuotation(line);
        String[] commandComponents = lineComponents[0].split(" ");

    }

    public String[] splitByQuotation(String input){
        String[] components = input.split("\"");
        for(String component : components){
            component = component.trim();
            //System.out.println(component);
        }
        return components;
    }

    public void adatTeszt(){
        movieController.addMovie("Spirited away", "animation", 126);
        for (Movie movie: movieController.getMovies()) {
            System.out.println(movie.getTitle());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Ticket service>");
        Scanner scanner = new Scanner(System.in);
        String line;
        boolean running = true;
        while(running) {
            line = scanner.nextLine();
            if("exit".equals(line))
                running = false;
            else
                inputInterpreter.interpret(line);
                //adatTeszt();
              //splitByQuotation(line);
            //System.out.println(line);
        }
    }
}
