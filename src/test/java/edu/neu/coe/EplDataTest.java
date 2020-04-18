package edu.neu.coe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.List;

public class EplDataTest {

    @Test
    public void currentGames() {
        EplData eplData = new EplData();
        List<GameOutcome> total = eplData.getDataset();
        Assert.assertEquals(total.size(), 288);
    }

    @Test
    public void remainingGamesTest() {
        EplData eplData = new EplData();
        List<GameOutcome> rg = eplData.getRemainingGamesData();
        Assert.assertEquals(rg.size(),92);
    }
}
