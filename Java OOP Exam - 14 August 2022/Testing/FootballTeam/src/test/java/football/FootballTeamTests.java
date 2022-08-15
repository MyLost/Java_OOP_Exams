package football;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FootballTeamTests {

    private FootballTeam team;
    private Footballer footballerOne;
    private Footballer footballerTwo;

    @Before
    public void setUp() {
        footballerOne = new Footballer("Messi");
        footballerTwo = new Footballer("Ronaldo");
        team = new FootballTeam("Barcelona", 2);
    }


    @Test
    public void test_getName() {
        Assert.assertEquals("Barcelona", team.getName());
    }

    @Test(expected = NullPointerException.class)
    public void test_setName() {
        team = new FootballTeam("", 4);
    }

    @Test
    public void test_vacationPoosition() {
       Assert.assertEquals(2, team.getVacantPositions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_setVacation() {
        team = new FootballTeam("Barcelona", -4);
    }

    @Test
    public void test_footballers() {
        Assert.assertEquals(0, team.getCount());
    }

    @Test
    public void test_addFootbollar() {
        team.addFootballer(footballerOne);
        team.addFootballer(footballerTwo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_addFootbollar_vacation() {
        team.addFootballer(footballerOne);
        team.addFootballer(footballerTwo);
        team.addFootballer(new Footballer("Berbatov"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_removeFootbollar() {
        team.removeFootballer("Berbatov");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_footballerForSale() {
        team.footballerForSale("Berbatov");
    }


    @Test
    public void test_footballerForSale_succes() {
        team.addFootballer(footballerTwo);
        team.addFootballer(footballerOne);
        Assert.assertEquals(footballerOne.getName(), team.footballerForSale(footballerOne.getName()).getName());
        Assert.assertFalse(footballerOne.isActive());
        Assert.assertTrue(footballerTwo.isActive());
    }



    @Test
    public void test_statistics() {
        team.addFootballer(footballerTwo);
        Assert.assertEquals(String.format("The footballer %s is in the team %s.", footballerTwo.getName(), team.getName()), team.getStatistics());
    }


}
