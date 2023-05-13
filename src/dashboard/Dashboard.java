package dashboard;

import java.util.ArrayList;

public class Dashboard {
    private String firstPlayerName = "";
    private String secondPlayerName = "";
    private final ArrayList<String> firstPlayerGames;
    private final ArrayList<String> secondPlayerGames;

    public Dashboard(String firstPlayerName, String secondPlayerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerGames  = new ArrayList<>();
        this.secondPlayerGames = new ArrayList<>();
    }

    public void setGamesSet(int firstPlayerGamesSet, int secondPlayerGamesSet){
        firstPlayerGames.add(String.valueOf(firstPlayerGamesSet));
        secondPlayerGames.add(String.valueOf(secondPlayerGamesSet));
    }

    public void setNamesInDashboard(String[][] dashboard){
        dashboard[0][0] = firstPlayerName;
        dashboard[1][0] = secondPlayerName;
    }
    public void setGamesInDashboard(String[][] dashboard, int size){
        setNamesInDashboard(dashboard);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size - 1 ; j++) {
                dashboard[i][j+1] = i < 1 ? firstPlayerGames.get(j) : secondPlayerGames.get(j);
            }
        }
    }
    public void printMatch() {
        int xSize = firstPlayerGames.size() + 1;
        String[][] dashboard = new String[2][xSize];

        setGamesInDashboard(dashboard, xSize);

        System.out.println();
        System.out.println("Resultado del partido");
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < xSize; j++) {
                System.out.print("|" + dashboard[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println();
    }
}