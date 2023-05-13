package test;

import gameManager.GameManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import players.Players;

public class GameManagerTest {

    @DisplayName("Al asignar el número de sets, corroboramos que se haya asignado el correcto.")
    @Test
    public void testSetQtySets(){
        GameManager gameManager = new GameManager();

        gameManager.setQtySets(5);
        int qtySets = gameManager.getQtySets();

        Assertions.assertEquals(5, qtySets);
    }

    @DisplayName("Chequearemos que esté funcionando de manera correcta el tie break forzando a que gane el jugador Sebastian")
    @Test
    public void testCheckTieBreakWinner(){
        Players firstPlayer = new Players();
        Players secondPlayer = new Players();
        GameManager gameManager = new GameManager();

        firstPlayer.setName("Sebastian");
        secondPlayer.setName("Martin");
        firstPlayer.setProbabilityWin(100);
        secondPlayer.setProbabilityWin(0);

        boolean expectedResult = gameManager.tieBreak(firstPlayer, secondPlayer);

        Assertions.assertTrue(expectedResult);
    }

    @DisplayName("Comprobaremos que el método getWinner anuncie el ganador correcto.")
    @Test
    public void testCheckWinnerName(){
        GameManager gameManager = new GameManager();
        Players firstPlayer = new Players();
        Players secondPlayer = new Players();
        int topSets = 0;

        firstPlayer.setName("Sebastian");
        secondPlayer.setName("Martin");
        firstPlayer.setSets(3);
        secondPlayer.setSets(2);
        gameManager.setQtySets(5);
        topSets = (gameManager.getQtySets() == 5) ? 3 : 2;

        String expectedResult = gameManager.getWinner(firstPlayer, secondPlayer, topSets);

        Assertions.assertEquals("Sebastian", expectedResult);
    }
}
