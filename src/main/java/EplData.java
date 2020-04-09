package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EplData {

    public static void getData(String filename) {
        File file = new File(filename);
        try{
            Scanner inputStream = new Scanner(file);
            while(inputStream.hasNext()) {
                String data = inputStream.next();
                System.out.println(data);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[] fileNames = new String[]{"src/data/2019-2020.csv","src/data/2018-2019.csv"};
        for(String filename : fileNames) {
            getData(filename);
        }
    }
}
