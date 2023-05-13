package players;

public class Players {
    private String name = "";
    private int probabilityWin = 0;
    private int points = 0;
    private String dashboardPoints = "";
    private int sets = 0;
    private int games = 0;


    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getDashboardPoints() {
        return dashboardPoints;
    }

    public void setDashboardPoints(String dashboardPoints) {
        this.dashboardPoints = dashboardPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProbabilityWin() {
        return probabilityWin;
    }

    public void setProbabilityWin(int probabilityWin) {
        this.probabilityWin = probabilityWin;
    }
}
