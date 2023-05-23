package gameManager;

import dashboard.Dashboard;
import players.Players;

public class GameManager {
    private String tournamentName = "";
    private int qtySets = 0;

    public int getQtySets() {
        return qtySets;
    }

    public void setQtySets(int qtySets) {
        this.qtySets = qtySets;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentName(){
        return this.tournamentName;
    }

    //Setea la probabilidad de ganar correspondiente de cada jugador.
    public void setProbabilityWin(Players firstPlayer, Players secondPlayer, int firstPlayerProbability) {
        int secondPlayerProbability = 100 - firstPlayerProbability;
        firstPlayer.setProbabilityWin(firstPlayerProbability);
        secondPlayer.setProbabilityWin(secondPlayerProbability);
    }

    //Maneja la lógica de quién gana el punto según su probabilidad de ganar
    public boolean probabilityPoint(Players firstPlayer, Players secondPlayer){
        int randomNumber = (int) (Math.random() * 100 + 1);
        boolean flag = false;

        if (firstPlayer.getProbabilityWin() > secondPlayer.getProbabilityWin() || firstPlayer.getProbabilityWin() == secondPlayer.getProbabilityWin()) {
            flag = randomNumber >= 1 && randomNumber <= firstPlayer.getProbabilityWin();
        }else {
            //Lo negamos para que retorne false cuando se cumpla esta condicion, ya que false representa al jugador 2
            flag = !(randomNumber >= 1 && randomNumber <= secondPlayer.getProbabilityWin());
        }
        return flag;
    }

    //Logica que maneja cuantos puntos suma según la cantidad de puntos que tiene actualmente
    public boolean points(Players firstPlayer, Players secondPlayer, boolean point){
        String winner = "";

        if ((firstPlayer.getPoints() == 0 || firstPlayer.getPoints() == 15) && point) {
            firstPlayer.setPoints(firstPlayer.getPoints() + 15);
        }else if((secondPlayer.getPoints() == 0 || secondPlayer.getPoints() == 15) && !point){
            secondPlayer.setPoints(secondPlayer.getPoints() + 15);
        }else if (firstPlayer.getPoints() == 30 && point) {
            firstPlayer.setPoints(firstPlayer.getPoints() + 10);
        }else if(secondPlayer.getPoints() == 30 && !point){
            secondPlayer.setPoints(secondPlayer.getPoints() + 10);
        }

        return point;
    }

    //Muestra el puntaje durante un game en tiempo real
    public void setGamePoints(Players firstPlayer, Players secondPlayer, int firstPlayerCount, int secondPlayerCount) {
        if(firstPlayerCount == 4 && secondPlayerCount < 3) {
            firstPlayer.setDashboardPoints("x");
            secondPlayer.setDashboardPoints(Integer.toString(secondPlayer.getPoints()));
        }else if(secondPlayerCount == 4 && firstPlayerCount < 3){
            secondPlayer.setDashboardPoints("x");
            firstPlayer.setDashboardPoints(Integer.toString(firstPlayer.getPoints()));
        }else {
            firstPlayer.setDashboardPoints(Integer.toString(firstPlayer.getPoints()));
            secondPlayer.setDashboardPoints(Integer.toString(secondPlayer.getPoints()));
        }
    }

    //Resetea los puntos cuando un jugador gana un game
    public void resetDashboardPoints(Players firstPlayer, Players secondPlayer){
        firstPlayer.setDashboardPoints("0");
        secondPlayer.setDashboardPoints("0");
    }

    //Muestra el resultado del partido en tiempo real
    public void currentDashboard(Players firstPlayer, Players secondPlayer){
        System.out.println(firstPlayer.getName()+": " + firstPlayer.getDashboardPoints() + " games: " + firstPlayer.getGames() + " sets: " + firstPlayer.getSets());
        System.out.println(secondPlayer.getName()+": " + secondPlayer.getDashboardPoints() + " games: " + secondPlayer.getGames() + " sets: " + secondPlayer.getSets());
        System.out.println();
    }

    //Logica que muestra el resultado del deuce en tiempo real
    public void setDeucePoints(Players firstPlayer, Players secondPlayer, int firstPlayerCount, int secondPlayerCount){
        if (firstPlayerCount == 4 && secondPlayerCount == 3) {
            firstPlayer.setDashboardPoints("Ad");
            secondPlayer.setDashboardPoints(Integer.toString(secondPlayer.getPoints()));
        }else if (secondPlayerCount == 4 && firstPlayerCount == 3) {
            secondPlayer.setDashboardPoints("Ad");
            firstPlayer.setDashboardPoints(Integer.toString(firstPlayer.getPoints()));
        }else if(firstPlayerCount == 3 && secondPlayerCount == 3){
            firstPlayer.setDashboardPoints(Integer.toString(firstPlayer.getPoints()));
            secondPlayer.setDashboardPoints(Integer.toString(secondPlayer.getPoints()));
        }else if(firstPlayerCount == 4 && secondPlayerCount == 4){
            firstPlayer.setDashboardPoints("Ad");
            secondPlayer.setDashboardPoints("Ad");
        }else if (firstPlayerCount == 5 && secondPlayerCount == 3) {
            firstPlayer.setDashboardPoints("x");
            secondPlayer.setDashboardPoints(Integer.toString(secondPlayer.getPoints()));
        }else if(secondPlayerCount == 5 && firstPlayerCount == 3){
            secondPlayer.setDashboardPoints("x");
            firstPlayer.setDashboardPoints(Integer.toString(firstPlayer.getPoints()));
        }
    }

    //Logica del deuce
    public boolean deuce(Players firstPlayer, Players secondPlayer, int firstPlayerCount, int secondPlayerCount){
        boolean flag = false;
        boolean winnerDeuce = false;

        while (!flag) {
            boolean point = probabilityPoint(firstPlayer, secondPlayer);

            if(firstPlayerCount == 5 && secondPlayerCount == 3){
                firstPlayer.setGames(firstPlayer.getGames() + 1);
                winnerDeuce = true;
                flag = true;
            }else if(secondPlayerCount == 5 && firstPlayerCount == 3){
                secondPlayer.setGames(secondPlayer.getGames() + 1);
                winnerDeuce = false;
                flag = true;
            }else if(firstPlayerCount == 4 && secondPlayerCount == 4){
                System.out.println("40 - 40 de nuevo!");
                firstPlayerCount--;
                secondPlayerCount--;
            }else if(point) {
                firstPlayerCount++;
            }else{
                secondPlayerCount++;
            }

            setDeucePoints(firstPlayer, secondPlayer, firstPlayerCount, secondPlayerCount);

            if (!flag) {
                System.out.println(firstPlayer.getName()+": " + firstPlayer.getDashboardPoints() + " games: " + firstPlayer.getGames() + " sets: " + firstPlayer.getSets());
                System.out.println(secondPlayer.getName()+": " + secondPlayer.getDashboardPoints() + " games: " + secondPlayer.getGames() + " sets: " + secondPlayer.getSets());
                System.out.println();
            }

            if (flag) {
                firstPlayer.setDashboardPoints("0");
                secondPlayer.setDashboardPoints("0");
            }
        }
        return winnerDeuce;
    }

    //Logica de games, también indica quién gana el game
    public void games(Players firstPlayer, Players secondPlayer){
        int firstPlayerCount = 0;
        int secondPlayerCount = 0;
        boolean point = false;
        boolean flag = false;
        String winner = "";

        resetDashboardPoints(firstPlayer, secondPlayer);
        while (!flag) {
            currentDashboard(firstPlayer, secondPlayer);
            point = probabilityPoint(firstPlayer, secondPlayer);

            flag = true;
            if (firstPlayerCount == 4 && secondPlayerCount < 3) {
                firstPlayer.setGames(firstPlayer.getGames() + 1);
                winner = firstPlayer.getName();
            }else if (secondPlayerCount == 4 && firstPlayerCount < 3) {
                secondPlayer.setGames(secondPlayer.getGames() + 1);
                winner = secondPlayer.getName();
            }else if(firstPlayerCount == 3 && secondPlayerCount == 3){
                System.out.println("Deuce!");
                System.out.println();
                winner = deuce(firstPlayer, secondPlayer, firstPlayerCount, secondPlayerCount) ? firstPlayer.getName() : secondPlayer.getName();
            }else{
                if (points(firstPlayer, secondPlayer, point)) {
                    firstPlayerCount++;
                }else {
                    secondPlayerCount++;
                }
                flag = false;
            }

            setGamePoints(firstPlayer, secondPlayer, firstPlayerCount, secondPlayerCount);

            if (flag) {
                System.out.println(winner +" ganó el game!");
                firstPlayer.setPoints(0);
                secondPlayer.setPoints(0);
            }
        }
    }

    //Muestra los puntos del tie break en tiempo real
    public void currentTieBreakPoints(Players firstPlayer, Players secondPlayer, int firstPlayerPoints, int secondPlayerPoints) {
        System.out.println(firstPlayer.getName()+": " + firstPlayerPoints);
        System.out.println(secondPlayer.getName()+": " + secondPlayerPoints);
        System.out.println();
    }

    //Indica si se debe jugar tie break
    public void isTieBreak(String player){
        System.out.println();
        System.out.println("Tie break!");
        System.out.println("Turno de saque para " + player);
    }

    //Maneja toda la lógica del tie break. Indica quién ganó el game
    public boolean tieBreak(Players firstPlayer, Players secondPlayer){
        boolean point = false;
        boolean flag = false;
        boolean winnerTieBreak = false;
        int firstPlayerPoints = 0;
        int secondPlayerPoints = 0;
        int differenceP1 = 0;
        int differenceP2 = 0;
        String winnerName = "";


        while (!flag) {
            differenceP1 = firstPlayerPoints - secondPlayerPoints;
            differenceP2 = secondPlayerPoints - firstPlayerPoints;
            point = probabilityPoint(firstPlayer, secondPlayer);

            currentTieBreakPoints(firstPlayer, secondPlayer, firstPlayerPoints, secondPlayerPoints);

            if ((firstPlayerPoints == 7 || firstPlayerPoints > 7) && differenceP1 >= 2) {
                firstPlayer.setGames(firstPlayer.getGames() + 1);
                firstPlayer.setSets(firstPlayer.getSets() + 1);
                winnerName = firstPlayer.getName();
                winnerTieBreak = true;
                flag = true;
            }else if ((secondPlayerPoints == 7 || secondPlayerPoints > 7) && differenceP2 >= 2) {
                secondPlayer.setGames(secondPlayer.getGames() + 1);
                secondPlayer.setSets(secondPlayer.getSets() + 1);
                winnerName = secondPlayer.getName();
                winnerTieBreak = false;
                flag = true;
            }else if (point){
                firstPlayerPoints++;
            }else{
                secondPlayerPoints++;
            }

            if (flag) {
                System.out.println(winnerName+" ganó el game!");
            }
        }
        return winnerTieBreak;
    }

    //Indica quién ganó el torneo al terminar el partido
    public String getWinner(Players firstPlayer, Players secondPlayer, int topSets){
        String winner = "";

        winner = firstPlayer.getSets() == topSets ? firstPlayer.getName() : secondPlayer.getName();
        System.out.println(winner + " ganó el torneo " + tournamentName + "!");

        return winner;
    }

    //Resetea los valores de ambos jugadores en caso de jugar revancha
    public void restartValues(Players firstPlayer, Players secondPlayer){
        firstPlayer.setGames(0);
        firstPlayer.setSets(0);
        firstPlayer.setPoints(0);
        secondPlayer.setGames(0);
        secondPlayer.setSets(0);
        secondPlayer.setPoints(0);
    }

    //Resetea los games para poder jugar uno nuevo
    public void resetGames(Players firstPlayer, Players secondPlayer){
        firstPlayer.setGames(0);
        secondPlayer.setGames(0);
    }

    //Indica si el juego está en condición de terminar.
    public boolean endGame(Players firstPlayer, Players secondPlayer, int topSets){
        return firstPlayer.getSets() >= topSets || secondPlayer.getSets() >= topSets;
    }

    public String serviceName(Players firstPlayer, Players secondPlayer, boolean service){
        return service ? firstPlayer.getName() : secondPlayer.getName();
    }
    //Maneja toda la logica del juego, indica si se juega game normal o tie break,
    //indica quien ganó el set
    public void match(Players firstPlayer, Players secondPlayer){
        int topSets = 0;
        boolean endMatch = false;
        boolean service = false;

        //La diferencia que necesita el jugador para ganar según la cantidad de sets que se van a jugar
        topSets = (qtySets == 5) ? 3 : 2;

        Dashboard dashboard = new Dashboard(firstPlayer.getName(), secondPlayer.getName());
        // Va a correr mientras no haya ganado ninguno de los dos
        while(!endMatch) {
            boolean flag = false;
            String player = "";
            String winner = "";

            //Sigue corriendo mientras ninguno haya llegado al top de sets
            while (!flag) {
                player = serviceName(firstPlayer,secondPlayer, service);

                // Logica que indica quién saca en caso de que todavía no haya ganado ninguno el partido o no haya tie break
                if (!endMatch) {
                    System.out.println();
                    System.out.println("Turno de saque para " + player);
                }
                flag = true;

                games(firstPlayer, secondPlayer);


                int differenceP1 = firstPlayer.getGames() - secondPlayer.getGames();
                int differenceP2 = secondPlayer.getGames() - firstPlayer.getGames();

                if ((firstPlayer.getGames() == 6 && differenceP1 >= 2) || (firstPlayer.getGames() == 7 && differenceP1 >= 1)) {
                    firstPlayer.setSets(firstPlayer.getSets() + 1);
                    winner = firstPlayer.getName();
                }else if ((secondPlayer.getGames() == 6 && differenceP2 >= 2) || (secondPlayer.getGames() == 7 && differenceP2 >= 1)) {
                    secondPlayer.setSets(secondPlayer.getSets() + 1);
                    winner = secondPlayer.getName();
                }else if (firstPlayer.getGames() == 6 && secondPlayer.getGames() == 6) {
                    service = !service;
                    player = serviceName(firstPlayer,secondPlayer, service);
                    isTieBreak(player);
                    winner = tieBreak(firstPlayer, secondPlayer) ? firstPlayer.getName() : secondPlayer.getName();
                }else{
                    flag = false;
                }
                service = !service;
                // Si uno de los dos llega a la cantidad de sets, endGame = true (termina el juego)
                endMatch = endGame(firstPlayer, secondPlayer, topSets);

                if (flag){
                    System.out.println(winner+" ganó el set!");
                    dashboard.setGamesSet(firstPlayer.getGames(),secondPlayer.getGames());
                    resetGames(firstPlayer, secondPlayer);
                }
            }
        }
        // Anuncia el ganador del partido
        getWinner(firstPlayer, secondPlayer, topSets);
        //Imprime el tablero final
        dashboard.printMatch();
    }
}