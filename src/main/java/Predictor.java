package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Predictor {

    List<GameOutcome> dataset;
    List<GameOutcome> remainingGames;
    Map<String, Integer> predictedRankings;

    Predictor(List<GameOutcome> dataset, List<GameOutcome> remainingGames, Map<String, Integer> currentRankings) {
        this.dataset = dataset;
        this.remainingGames = remainingGames;
        this.predictedRankings = new HashMap<>();
        for(Map.Entry<String,Integer> e: currentRankings.entrySet()) {
            predictedRankings.put(e.getKey(), e.getValue());
        }
    }

    public Map<String, Integer> getPredictedRankings() {
        for(GameOutcome g: remainingGames) {
            if(predictGame(g.getHomeTeam(),g.getAwayTeam()).equals(g.getHomeTeam())) {
                predictedRankings.put(g.getHomeTeam(), predictedRankings.getOrDefault(g.getHomeTeam(),0) + 3);
            }
            else if(predictGame(g.getHomeTeam(),g.getAwayTeam()).equals(g.getAwayTeam()))
                predictedRankings.put(g.getAwayTeam(),predictedRankings.getOrDefault(g.getAwayTeam(),0) + 3);
            else {
                predictedRankings.put(g.getAwayTeam(), predictedRankings.getOrDefault(g.getAwayTeam(),0) + 1);
                predictedRankings.put(g.getHomeTeam(), predictedRankings.getOrDefault(g.getHomeTeam(),0) + 1);
            }
        }
        return predictedRankings;
    }

    private String predictGame(String hometeam, String awayteam) {
        double probHwin = 0;
        double probAwin = 0;
        // prob = 0.4 * win prob in whole series + gdiff * 0.3 + 0.3 * win prob with team
        for(GameOutcome g: dataset) {
                if (g.getHomeTeam().equals(hometeam) && g.getAwayTeam().equals(awayteam)) {
                    if (g.getWinner() != null && g.getWinner().equals(hometeam))
                        probHwin = probHwin + 0.3 * 1;
                    else if (g.getWinner() != null && g.getWinner().equals(awayteam))
                        probAwin = probAwin + 0.3 * 1;
                } else if (g.getHomeTeam().equals(hometeam) && !g.getAwayTeam().equals(awayteam)) {
                    if (g.getWinner() != null && g.getWinner().equals(hometeam))
                        probHwin = probHwin + 0.4 * 1;
                    else
                        probHwin = probHwin - 0.4 * 1;
                }
        }
        if(probAwin > probHwin) {
            return awayteam;
        }
        else if(probAwin < probHwin)
            return hometeam;
        else
            return "";
    }
}
