package com.epam.training.ticketservice;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class InputInterpreter {


    private void movieHandler(String[] words){

    }

    private void roomHandler(String[] words){

    }

    public void interpret(String line){
        String[] words = line.split(" ");

        //TODO: problem if a single word is put in quotations like "aa"
        LinkedList<String> components = new LinkedList<>();
        for (int i = 0; i < words.length; i++){
            if(!words[i].contains("\"")){
                components.add(words[i]);
                System.out.println("added word: " + words[i]);
            } else{
                StringBuilder sb = new StringBuilder();
                sb.append(words[i].substring(1));
                sb.append(" ");
                System.out.println("appended to builder1 " + words[i]);
                for (int j = i+1;j < words.length; j++){
                    if(!words[j].contains("\"")){
                        sb.append(words[j]);
                        sb.append(" ");
                        System.out.println("appended to builder2 " + words[j]);
                    } else{
                        sb.append(words[j].substring(0, words[j].length()-1));
                        System.out.println("appended to builder3 " + words[j]);
                        components.add(sb.toString());
                        i = j;
                        j = words.length+ 10;
                    }
                }
            }
        }


        components.stream().forEach(System.out::println);

/*
        LinkedList<Integer> indexes = new LinkedList<>();
        for(int i = 0; i < words.length; i++){
            if(words[i].contains("\"")){
                indexes.add(i);
            }
        }

        LinkedList<String> subComponents = new LinkedList<>();
        String longWord;
        for(int i = 0; i < indexes.size()/2; i++){
            for (int j = indexes.get(i); j < indexes.get(i+1); j++){
                longWord.
            }
            subComponents.add()
        }

 */

        /*
        switch (words[1]){
            case "movie":
                System.out.println("passing to movie handler");
                break;
            case "room":
                System.out.println("passing to room handler");
                break;
        }
         */
    }




}
