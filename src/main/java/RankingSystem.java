package main.java;

import java.util.List;

public class RankingSystem {


    public static void main(String[] args) {
        // fetch data
        EplData epldata = new EplData();
        List<GameOutcome> dataset = epldata.getDataset();

        // do predictions
//        Predictor predictor = new Predictor();

        // print results

        // show results in web page, graphs etc
    }
}
