package edu.neu.coe;

import java.util.*;

public class RankingSystem {

    private static Map<String,Integer> teamRanking = new HashMap<>();

    public static Map<String, Integer> sortByValue(Map<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void getCurrentRankings(List<GameOutcome> data) {
        for(GameOutcome g: data) {
            if(g.getGoalDifference() > 0)
                teamRanking.put(g.getHomeTeam(), teamRanking.getOrDefault(g.getHomeTeam(),0) + 3);
            else if(g.getGoalDifference() < 0)
                teamRanking.put(g.getAwayTeam(), teamRanking.getOrDefault(g.getAwayTeam(), 0) + 3);
            else {
                teamRanking.put(g.getHomeTeam(), teamRanking.getOrDefault(g.getHomeTeam(),0) + 1);
                teamRanking.put(g.getAwayTeam(), teamRanking.getOrDefault(g.getAwayTeam(), 0) + 1);
            }
        }
        teamRanking = sortByValue(teamRanking);
        System.out.println("*********************** Current Standings of teams ********************");
        int rank = 0;
        System.out.format("%32s%10s%16s", "Team", "Score", "Rank\n");
        System.out.println("-----------------------------------------------------------------------------");
        for(Map.Entry<String,Integer> e: teamRanking.entrySet()){
            System.out.format("%32s%10d%16s", e.getKey(),  e.getValue(), ++rank + "\n");
        }
    }

    public static void main(String[] args) {

        // fetch data
        EplData epldata = new EplData();
        List<GameOutcome> dataset = epldata.getDataset();
        // print current rankings
        getCurrentRankings(dataset);

        // do predictions
        Predictor predictor = new Predictor(dataset, epldata.getRemainingGamesData(), teamRanking);

        // print predicted end of season rankings
        Map<String,Integer> predictedRankings = predictor.getPredictedRankings();
        predictedRankings = sortByValue(predictedRankings);
        int rank = 0;
        System.out.println("********************* My Predictions after whole Season ********************");
        System.out.format("%32s%10s%16s", "Team", "Score", "Rank\n");
        System.out.println("-----------------------------------------------------------------------------");
        for(Map.Entry<String,Integer> e: predictedRankings.entrySet()){
            System.out.format("%32s%10d%16s", e.getKey(),  e.getValue(), ++rank + "\n");
        }

        // show results in web page, graphs etc
    }
}
