import java.util.Random;
import java.util.Scanner;

public class CasinoRoulette {
  private static final int MIN_BET = 5;
  private static final int MAX_BET = 100;
  private static final int NUM_SLOTS = 37; // incluye el 0

  private static int balance = 1000; // saldo inicial
  private static int betAmount;
  private static int betType;
  private static int winningSlot;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    while (balance > 0) {
      System.out.println("Tu saldo actual es de: " + balance);
      System.out.println("¿Cuánto dinero quieres apostar? (mínimo " + MIN_BET + ")");
      betAmount = scanner.nextInt();

      if (betAmount < MIN_BET) {
        System.out.println("La apuesta mínima es de " + MIN_BET);
        continue;
      }

      if (betAmount > balance) {
        System.out.println("No tienes suficiente saldo para esa apuesta");
        continue;
      }

      System.out.println("¿En qué quieres apostar? (1 = rojo, 2 = negro, 3 = par, 4 = impar)");
      betType = scanner.nextInt();

      // gira la ruleta
      winningSlot = random.nextInt(NUM_SLOTS);
      System.out.println("La bola cayó en el slot " + winningSlot);

      // comprueba si ganó o perdió
      if (checkWin()) {
        balance += betAmount;
        System.out.println("¡Felicidades, has ganado! Tu saldo actual es de " + balance);
      } else {
        balance -= betAmount;
        System.out.println("Lo siento, has perdido. Tu saldo actual es de " + balance);
      }
    }

    System.out.println("Lo siento, has quedado sin saldo. ¡Vuelve pronto!");
  }

  private static boolean checkWin() {
    // comprueba si el slot es rojo o negro
    if (betType == 1 && isRed()) {
      return true;
    } else if (betType == 2 && !isRed()) {
      return true;
    }

    // comprueba si el slot es par o impar
    if (betType == 3 && winningSlot % 2 == 0) {
      return true;
    } else if (betType == 4 && winningSlot % 2 != 0) {
      return true;
    }

    return false;
  }

  private static boolean isRed() {
    return winningSlot == 1 || winningSlot == 3 || winningSlot == 5 || winningSlot == 7 || winningSlot == 9
        || winningSlot == 12 || winningSlot == 14 || winningSlot == 16 ||winningSlot == 18 || winningSlot == 19 || winningSlot == 21 || winningSlot == 23 || winningSlot == 25
|| winningSlot == 27 || winningSlot == 30 || winningSlot == 32 || winningSlot == 34 || winningSlot == 36;
}
}
