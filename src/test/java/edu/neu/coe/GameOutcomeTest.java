package edu.neu.coe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.List;

public class GameOutcomeTest {

    @Test
    public void GameTest() {
        GameOutcome g = new GameOutcome("teama", "teamb", 4, 2);
        Assert.assertEquals(g.getGoalDifference(), 2);
    }

    @Test
    public void GameTest2() {
        GameOutcome g = new GameOutcome("teamc", "teamd", 4, 6);
        Assert.assertEquals(g.getGoalDifference(), -2);
    }

}
