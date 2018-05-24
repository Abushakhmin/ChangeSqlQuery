package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        int n = args.length;

        if(n == 2) {
            String pathSql = args[0];
            String pathSqlNew = args[1];
//        long startTime = System.currentTimeMillis();
            update(pathSql, pathSqlNew);
//        long timeSpent = System.currentTimeMillis() - startTime;
//        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
        } else {
            System.out.println("Ошибка ввода аргументов!");
        }
    }


    private static void update(String pathSql, String pathSqlNew) {

        try {
            BufferedReader file = new BufferedReader(new FileReader(pathSql));
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

            FileWriter fileWriter = new FileWriter(pathSqlNew);
            fileWriter.write(inputStr);
            fileWriter.flush();

//            System.out.println(inputStr);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem reading file.");
        }
    }
}
