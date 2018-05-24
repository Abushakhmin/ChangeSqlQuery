package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {
//        long startTime = System.currentTimeMillis();
        update();
//        long timeSpent = System.currentTimeMillis() - startTime;
//        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
    }


    private static void update() {

        try {
            BufferedReader file = new BufferedReader(new FileReader("/home/roman/example"));
            String line;
            StringBuilder inputBuilder = new StringBuilder();

            while ((line = file.readLine()) != null) {

                if (line.startsWith("ALTER TABLE ")){
                    line = line.replaceAll("ADD CONSTRAINT (.*?) FOREIGN", "ADD FOREIGN");
                }

                if (line.startsWith("CREATE UNIQUE INDEX")){
                    line = line.replaceAll("CREATE UNIQUE INDEX ","CREATE UNIQUE INDEX \"PUBLIC\".\"");
                    line = line.replaceFirst(" ON ","\" ON public.");
                }

                inputBuilder.append(line);
                inputBuilder.append('\n');
            }

            String inputStr = inputBuilder.toString();

            file.close();

            FileWriter fileWriter = new FileWriter("/home/roman/example2");
            fileWriter.write(inputStr);
            fileWriter.flush();

            System.out.println(inputStr);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem reading file.");
        }
    }
}
