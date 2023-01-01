
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RuletaCasino extends JFrame implements ActionListener {

    private static final int NUM_BOTONES = 36;
    private static final int NUM_OPCIONES = 6;
    private static final int MONTO_APUESTA = 1;
    private int saldo = 10;
    private ArrayList<Apuesta> listaApuestas;

    private JLabel lblSaldo;
    private JButton[] btnNumeros = new JButton[NUM_BOTONES];
    private JButton[] btnOpciones = new JButton[NUM_OPCIONES];
    private JButton btnGirar;

    public RuletaCasino() {

        listaApuestas = new ArrayList<>();
        // Creamos la ventana principal
        setTitle("Ruleta Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Creamos el panel de los botones de número
        JPanel panelNumeros = new JPanel();
        panelNumeros.setLayout(new GridLayout(6, 6));
        for (int i = 0; i < NUM_BOTONES; i++) {
            btnNumeros[i] = new JButton(String.valueOf(i + 1));
            btnNumeros[i].addActionListener(this);
            panelNumeros.add(btnNumeros[i]);
        }

        // Creamos el panel de los botones de opción
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new FlowLayout());
        String[] opciones = {"Par", "Impar", "Rojo", "Negro", "1ª mitad", "2ª mitad"};
        for (int i = 0; i < NUM_OPCIONES; i++) {
            btnOpciones[i] = new JButton(opciones[i]);
            btnOpciones[i].addActionListener(this);
            panelOpciones.add(btnOpciones[i]);
        }

        // Creamos el botón de girar la ruleta
        btnGirar = new JButton("Girar");
        btnGirar.addActionListener(this);

        // Creamos el panel de información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout());
        lblSaldo = new JLabel("Saldo: " + saldo + "€");
        panelInfo.add(lblSaldo);

        // Añadimos los paneles a la ventana principal
        add(panelNumeros);
        add(panelOpciones);
        add(btnGirar);
        add(panelInfo);

        // Mostramos la ventana
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        String tipo = "";
        if (((JButton) fuente).getText().equals("Girar")) {
            System.out.println("Gira la ruleta");
            // Si se ha pulsado el botón de girar, generamos el número ganador y comprobamos las apuestas
            int numeroGanador = (int) (Math.random() * NUM_BOTONES);
            comprobarApuestas(numeroGanador);
        } else {
            // Si se ha pulsado un botón de número o opción, añadimos una apuesta
            String opcion = ((JButton) fuente).getText();
            if (opcion.equals("Par") || opcion.equals("Impar") || opcion.equals("Rojo") || opcion.equals("Negro") || opcion.equals("1ª mitad") || opcion.equals("2ª mitad")) {
                // Si se trata de un botón de opción, añadimos una apuesta de 1€
                if (saldo >= MONTO_APUESTA) {
                    saldo -= MONTO_APUESTA;
                    lblSaldo.setText("Saldo: " + saldo + "€");
                    System.out.println("Apuesta a " + opcion + " por " + MONTO_APUESTA + "€");
                } else {
                    System.out.println("No tienes suficiente saldo para hacer una apuesta");
                }
                switch (opcion) {
                    case "Par":
                    case "Impar":
                        tipo = "parimpar";
                        break;
                    case "Rojo":
                    case "Negro":
                        tipo = "color";
                        break;
                    default:
                        tipo = "mitad";
                        break;
                }

            } else {
                // Si se trata de un botón de número, añadimos una apuesta de 1€ al número correspondiente

                int numero = Integer.parseInt(opcion);
                tipo = "numero";
                if (saldo >= MONTO_APUESTA) {
                    saldo -= MONTO_APUESTA;
                    lblSaldo.setText("Saldo: " + saldo + "€");
                    System.out.println("Apuesta al número " + numero + " por " + MONTO_APUESTA + "€");
                } else {
                    System.out.println("No tienes suficiente saldo para hacer una apuesta");
                }
            }
            System.out.println(tipo);
            System.out.println(opcion);
            listaApuestas.add(new Apuesta(tipo, opcion, 1));

        }
    }

    private void comprobarApuestas(int numeroGanador) {
        //generamos dos listas una con los numeros rojos y otra con los nuimeros negros
        int[] numerosRojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        int[] numerosNegros = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
        System.out.println("Tus apuestas son: " );
        for (Apuesta apuesta : listaApuestas) {
            System.out.println(apuesta.getTipo() + " - " + apuesta.getOpcion() + " - " + apuesta.getMonto() + "€");
        }
        System.out.println("Comprobando apuestas");
        for (Apuesta apuesta : listaApuestas) {
            String tipo = apuesta.getTipo();
            String opcion = apuesta.getOpcion();
            int monto = apuesta.getMonto();
            int beneficio = 0; // Beneficio de la apuesta (si es ganadora)
            System.out.println("Apuesta: " + tipo + " - " + opcion + " - " + monto + "€");
            switch (tipo) {
                case "numero":
                    // Si la apuesta es a un número, comprobamos si coincide con el número ganador
                    System.out.println("Apuesta a número: " + opcion + " - Número ganador: " + numeroGanador);
                    if (opcion.equals(String.valueOf(numeroGanador))) {
                        // Si coincide, la apuesta es ganadora y calculamos el beneficio
                        beneficio = monto * 36;
                    }   break;
                case "parimpar":
                    //comprobamos que la opcion sea par e impar y que el numero sea par o impar
                    System.out.println("Apuesta a " + opcion + " - Número ganador: " + numeroGanador);
                    if (opcion.equals("par") && numeroGanador % 2 == 0) {
                        beneficio = monto * 2;
                    } else if (opcion.equals("impar") && numeroGanador % 2 != 0) {
                        beneficio = monto * 2;
                    }   break;
                case "color":
                    //comprobamos que la opcion sea rojo y que el numero sea igual al color
                    System.out.println("Apuesta a " + opcion + " - Número ganador: " + numeroGanador);
                    for (int i = 0; i < numerosRojos.length; i++) {
                        if (opcion.equals("rojo") && numerosRojos[i] == numeroGanador) {
                            beneficio = monto * 2;
                        } else if (opcion.equals("negro") && numerosNegros[i] == numeroGanador) {
                            beneficio = monto * 2;
                        } 
                    }   break;
                case "mitad":

                    // Si la apuesta es a la primera o segunda mitad, comprobamos a cuál pertenece el número ganador
                    System.out.println("Apuesta a " + opcion + " - Número ganador: " + numeroGanador);
                    if (opcion.equals("1ª mitad") && numeroGanador <= 18) {
                        beneficio = monto * 2;
                    } else if (opcion.equals("2ª mitad") && numeroGanador > 18) {
                        beneficio = monto * 2;
                    }   break;
                default:
                System.out.println("Apuesta no válida");
                    break;
            }

            // Añadimos el beneficio al saldo actual
            saldo += beneficio;

           
        }
        // Mostramos el saldo actual
        System.out.println("Saldo: " + saldo + "€");
        lblSaldo.setText("Saldo: " + saldo + "€");
    }

    public static void main(String[] args) {
        new RuletaCasino();

    }

    public class Apuesta {

        private String tipo;
        private String opcion;
        private int monto;

        public Apuesta(String tipo, String opcion, int monto) {
            this.tipo = tipo;
            this.opcion = opcion;
            this.monto = monto;
        }

        public String getTipo() {
            return tipo;
        }

        public String getOpcion() {
            return opcion;
        }

        public int getMonto() {
            return monto;
        }

        public String toString() {
            return "Apuesta de " + monto + "€ al " + tipo + " " + opcion;
        }
    }

}
