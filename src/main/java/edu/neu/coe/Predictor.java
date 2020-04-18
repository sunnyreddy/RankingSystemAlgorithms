package edu.neu.coe;

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
            if(pdfPrediction(g.getHomeTeam(),g.getAwayTeam()).equals(g.getHomeTeam())) {
                predictedRankings.put(g.getHomeTeam(), predictedRankings.getOrDefault(g.getHomeTeam(),0) + 3);
            }
            else if(pdfPrediction(g.getHomeTeam(),g.getAwayTeam()).equals(g.getAwayTeam()))
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

    private List<Double> normalDistributionPdf(String team) {
        List<Double> meanSd = new ArrayList<>();
        double mean = 0, total = 0,standD = 0, size = 0;
        for(GameOutcome g: dataset) {
            if(g.getHomeTeam().equals(team) || g.getAwayTeam().equals(team)) {
                total += g.getGoalDifference();
                size++;
            }
        }
        mean = total/size;

        total = 0;
        for(GameOutcome g: dataset) {
            if(g.getHomeTeam().equals(team) || g.getAwayTeam().equals(team))
                total += Math.pow((g.getGoalDifference() - mean), 2);
        }
        standD = Math.sqrt(total/size);

        meanSd.add(mean);
        meanSd.add(standD);
        return meanSd;
    }

    private double getNormalProbabilityAtX(double x, double mean, double std) {
        return 1 / (std * Math.sqrt(2 * 3.14)) * (1 / Math.exp(Math.pow(x - mean, 2) / 2 * Math.pow(std, 2)));
    }

    private double getAreaUnderNormalCurve(double z1, double z2, double mean, double std) {
        double area = 0.0;
        final int rectangles = 100000; // more rectangles = more precise, less rectangles = quicker execution
        final double width = (z2 - z1) / rectangles;
        for(int i = 0; i < rectangles; i++)
            area += width * getNormalProbabilityAtX(width * i + z1, mean, std);
        //System.out.println(area);
        return area;
    }

    private String pdfPredictionTrail(String hometeam, String awayteam) {

        List<Double> pdfHome = normalDistributionPdf(hometeam);
        List<Double> pdfAway = normalDistributionPdf(awayteam);

        double probH = 0, probA = 0;
        for(int i=1; i<=6; i++) {
            probH += 1 / (pdfHome.get(1) * Math.sqrt(2 * 3.14)) * (1 / Math.exp(Math.pow(i - pdfHome.get(0), 2) / 2 * Math.pow(pdfHome.get(1), 2)));
            probA += 1 / (pdfAway.get(1) * Math.sqrt(2 * 3.14)) * (1 / Math.exp(Math.pow(i - pdfAway.get(0), 2) / 2 * Math.pow(pdfAway.get(1), 2)));
        }

        System.out.println("Prob of " + hometeam + " winning " + probH + " Prob of " + awayteam + " winning " + probA);

        if(probA > probH)
            return awayteam;
        else if(probA < probH)
            return hometeam;
        else
            return "";
    }

    private String pdfPrediction(String hometeam, String awayteam) {

        List<Double> pdfHome = normalDistributionPdf(hometeam);
        List<Double> pdfAway = normalDistributionPdf(awayteam);

        double probH = 0, probA = 0;
        probH = getAreaUnderNormalCurve(1, 8, pdfHome.get(0), pdfHome.get(1));
        probA = getAreaUnderNormalCurve(1, 8, pdfAway.get(0), pdfAway.get(1));

        //System.out.println("Prob of " + hometeam + " winning " + probH + " Prob of " + awayteam + " winning " + probA);

        if(probA > probH)
            return awayteam;
        else if(probA < probH)
            return hometeam;
        else
            return "";
    }
}
