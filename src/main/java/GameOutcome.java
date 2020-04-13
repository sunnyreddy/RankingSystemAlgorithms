package main.java;

public class GameOutcome {

    private  String homeTeam;
    private String awayTeam;
    private String winner;
    private boolean isDraw = false;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private int goalDifference;

    GameOutcome(String homeTeam, String awayTeam, int homeTeamGoals, int awayTeamGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.goalDifference = homeTeamGoals - awayTeamGoals;
        if(homeTeamGoals > awayTeamGoals)
            this.winner = new String(homeTeam);
        else if(awayTeamGoals > homeTeamGoals)
            this.winner = new String(awayTeam);
        else
            this.isDraw = true;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    @Override
    public String toString() {
        return "homeTeam = " + homeTeam + " awayTeam = " + awayTeam + " goalDifference = " + goalDifference;
    }
}
