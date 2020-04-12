package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Class to get data from csv files and load in application
public class EplData {

    private List<GameOutcome> dataset = new ArrayList<>();
    private Pattern pattern = Pattern.compile(",");

    public EplData() {
        String[] fileNames = new String[]{"src/data/2019-2020.csv"};
        for(String filename : fileNames) {
            getDataFromCsv(filename);
        }
    }

    private void getDataFromCsv(String filename) {
        File file = new File(filename);
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            dataset = in.lines().skip(1).map( line -> {
                String[] x = pattern.split(line);
                return new GameOutcome(x[3],x[4],Integer.parseInt(x[5]),Integer.parseInt(x[6]));
            }).collect(Collectors.toList());

//            Scanner inputStream = new Scanner(file);
//            int check = 0;
//            while(inputStream.hasNext()) {
//                String data = inputStream.next();
//                if(check == 0)
//                    check = 1;
//                else{
//                    String[] x = pattern.split(data);
//                    GameOutcome go = new GameOutcome(x[3],x[4],Integer.parseInt(x[5]),Integer.parseInt(x[6]));
//                    System.out.println(data);
//                }
//            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected List<GameOutcome> getDataset() {
        return dataset;
    }

}
