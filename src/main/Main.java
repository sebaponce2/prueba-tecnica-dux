package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import gameManager.GameManager;
import players.Players;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        GameManager gameManager = new GameManager();
        Players firstPlayer = new Players();
        Players secondPlayer = new Players();
        int option = 0;

        System.out.println("¡Bienvenido! Por favor, escriba el nombre del torneo.");
        gameManager.setTournamentName(input.nextLine());

        System.out.println("Por favor, escriba el nombre del primer jugador.");
        firstPlayer.setName(input.nextLine());

        System.out.println("Por favor, ingrese el nombre del segundo jugador.");
        secondPlayer.setName(input.nextLine());

        System.out.println("¿Desea jugar 3 sets o 5 sets?");
        while(true){
            try{
                option = input.nextInt();
                if (option != 3 && option != 5){
                    throw new IllegalArgumentException();
                }else{
                    gameManager.setQtySets(option);
                    System.out.println("Usted eligió " + gameManager.getQtySets()+ " sets.");
                }
                break;
            }catch(IllegalArgumentException e){
                System.out.println("Por favor, indique si quiere jugar 3 sets o 5 sets.");
                input.nextLine();
            }
        }

        System.out.println("Por favor, ingrese la probabilidad de ganar del primer jugador del 1 al 100.");
        while (true){
            try {
                int firstPlayerProbability = input.nextInt();
                if (firstPlayerProbability > 0 && firstPlayerProbability <= 100){
                    gameManager.setProbabilityWin(firstPlayer, secondPlayer, firstPlayerProbability);
                    System.out.println("La probabilidad de ganar del primer jugador es de " + firstPlayer.getProbabilityWin() + "% y la del segundo jugador es de " + secondPlayer.getProbabilityWin() + "%");
                }else{
                    throw new InputMismatchException();
                }
                break;
            }catch(InputMismatchException e){
                System.out.println("Por favor, elija la probabilidad entre 1 y 100.");
                input.nextLine();
            }
        }
        gameManager.match(firstPlayer, secondPlayer);

        while(true){
            try {
                System.out.println("¿Desea jugar la revancha? Presione 1 (SI) o 2 (NO)");
                option = input.nextInt();
                if (option == 1){
                    gameManager.restartValues(firstPlayer, secondPlayer);
                    gameManager.match(firstPlayer, secondPlayer);
                }else if(option < 1 || option > 2){
                    throw new IllegalArgumentException();
                }else{
                    break;
                }
            }catch(IllegalArgumentException e){
                System.out.println("Por favor, seleccione una opcion correcta.");
                input.nextLine();
            }
        }
        input.close();
    }
}