import java.util.Random;

public class Ruleta {
    private int[] numeros = new int[37];  // Lista de números de la ruleta (del 1 al 36)
    private String[] colores = {"rojo", "negro"};  // Lista de colores de la ruleta (rojo y negro)
    private int giroActual;  // Número del giro actual de la ruleta
    private double dineroJugador;  // Cantidad de dinero del jugador

    // Constructor de la clase Ruleta
    public Ruleta(double dineroInicial) {
        // Inicializamos la lista de números y el dinero del jugador
        for (int i = 0; i < 37; i++) {
            numeros[i] = i;
        }
        dineroJugador = dineroInicial;
    }

    // Método para girar la ruleta y actualizar el giro actual
    public void girarRuleta(double cantidadApostada) {
        Random rnd = new Random();
        giroActual = rnd.nextInt(37);  // Generamos un número aleatorio entre 0 y 36
        dineroJugador -= cantidadApostada;  // Restamos la cantidad apostada al dinero del jugador
    }

    // Método para obtener el color del número en el giro actual de la ruleta
    public String getColor() {
        if (giroActual % 2 == 0) {
            return "negro";
        } else {
            return "rojo";
        }
    }

    // Método para realizar una apuesta a un número o a un color
    public void apostar(int numero, double cantidad) {
        // Si el jugador ha apostado a un número
        if (numero >= 0 && numero <= 36) {
            // Si ha acertado la apuesta, se le añade la cantidad apostada al dinero del jugador
            if (numero == giroActual) {
                dineroJugador += cantidad;
            }
            // Si no ha acertado, se le resta la cantidad apostada al dinero del jugador
            else {
                dineroJugador -= cantidad;
            }
        }
        // Si el jugador ha apostado a un color
        else if (numero == -1 || numero == -2) {
            // Si ha acertado la apuesta, se le añade la cantidad apostada al dinero del jugador
            if ((numero == -1 && getColor().equals("rojo")) || (numero == -2 && getColor().equals("negro"))) {
                dineroJugador += cantidad;
            }
            // Si no ha acertado, se le resta la cantidad apostada al dinero del jugador
            else {dineroJugador -= cantidad;
}
}
}
// Método para jugar a la ruleta
public void jugar() {
    Scanner sc = new Scanner(System.in);

    // Repetimos el juego mientras el jugador tenga dinero
    while (dineroJugador > 0) {
        // Pedimos al jugador que realice una apuesta
        System.out.println("Tienes " + dineroJugador + " euros. ¿Qué quieres apostar?");
        System.out.println("1) Número (entre 0 y 36)");
        System.out.println("2) Color (-1 para rojo y -2 para negro)");
        int numeroApostado = sc.nextInt();
        System.out.println("¿Cuánto dinero quieres apostar?");
        double cantidadApostada = sc.nextDouble();

        // Giramos la ruleta y actualizamos el giro actual
        girarRuleta(cantidadApostada);

        // Realizamos la apuesta y comprobamos si ha ganado o perdido
        apostar(numeroApostado, cantidadApostada);
        if (dineroJugador >= cantidadApostada) {
            System.out.println("¡Has ganado!");
        } else {
            System.out.println("Lo siento, has perdido.");
        }
    }

    // Si el jugador no tiene dinero, se muestra un mensaje y se finaliza el juego
    System.out.println("Lo siento, no tienes dinero suficiente para seguir jugando.");
}}

               
