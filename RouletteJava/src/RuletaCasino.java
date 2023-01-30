
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class RuletaCasino extends JFrame implements ActionListener {

    private static final int NUM_BOTONES = 37;
    private static final int NUM_OPCIONES = 6;
    private static final int MONTO_APUESTA = 1;
    private int saldo = 10;
    private ArrayList<Apuesta> listaApuestas;

    private JLabel lblApuestas;
    private JLabel lblSaldo;
    private JLabel lblUltimoNum;
    private JButton[] btnNumeros = new JButton[NUM_BOTONES];
    private JButton[] btnOpciones = new JButton[NUM_OPCIONES];
    private JButton btnGirar;

    public RuletaCasino() {

        listaApuestas = new ArrayList<>();
        // Creamos la ventana principal
        setTitle("Ruleta Casino");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        //color de fondo verde oscuro
        getContentPane().setBackground(java.awt.Color.DARK_GRAY);

        int[] numerosRojos = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36 };
        int[] numerosNegros = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35 };
        // Creamos el panel de los botones de número
        JPanel panelNumeros = new JPanel();
        panelNumeros.setLayout(new GridLayout(12, 3));

        btnNumeros[0] = new JButton(String.valueOf(0));
        btnNumeros[0].setBackground(java.awt.Color.GREEN);
        // definimos el tmaño del boton 0
        btnNumeros[0].setPreferredSize(new java.awt.Dimension(50, 300));

        btnNumeros[0].addActionListener(this);
        JPanel panelNumero0 = new JPanel();
        panelNumero0.setLayout(new FlowLayout());
        panelNumero0.add(btnNumeros[0]);

        // el boton 0 ocupa 3 espacios

        for (int i = 0; i < NUM_BOTONES; i++) {
            // si el numero es 0 el boton es de color verde

            // si el numero esta dentro de la array numerosRojos
            for (int j = 0; j < numerosRojos.length; j++) {
                if (i == numerosRojos[j]) {
                    btnNumeros[i] = new JButton(String.valueOf(i));
                    btnNumeros[i].setBackground(java.awt.Color.RED);
                    btnNumeros[i].addActionListener(this);
                    panelNumeros.add(btnNumeros[i]);
                    continue;
                }
            }
            // si el numero esta dentro de la array numerosNegros
            for (int j = 0; j < numerosNegros.length; j++) {
                if (i == numerosNegros[j]) {
                    btnNumeros[i] = new JButton(String.valueOf(i));
                    btnNumeros[i].setBackground(java.awt.Color.BLACK);
                    btnNumeros[i].addActionListener(this);
                    panelNumeros.add(btnNumeros[i]);
                    continue;
                }
            }
        }

        // Creamos el panel de los botones de opción
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new FlowLayout());
        String[] opciones = { "Par", "Impar", "Rojo", "Negro", "1ª mitad", "2ª mitad" };
        for (int i = 0; i < NUM_OPCIONES; i++) {
            btnOpciones[i] = new JButton(opciones[i]);
            btnOpciones[i].addActionListener(this);
            panelOpciones.add(btnOpciones[i]);
        }

        // Creamos el botón de girar la ruleta
        btnGirar = new JButton("Girar");
        btnGirar.addActionListener(this);
        //no es visible hasta que se apueste
        btnGirar.setVisible(false);

        // Creamos el panel de información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout());
        lblSaldo = new JLabel("Saldo: " + saldo + "€");
        panelInfo.add(lblSaldo);

        lblApuestas = new JLabel("Apuestas: ");

        panelInfo.add(lblApuestas);
        // Creamos el panel de información
        JPanel panelInfo2 = new JPanel();
        panelInfo2.setLayout(new FlowLayout());
        lblUltimoNum = new JLabel("Numero salido: ");
        panelInfo2.add(lblUltimoNum);

        // Añadimos los paneles a la ventana principal
        add(panelNumero0);
        add(panelNumeros);
        add(panelOpciones);
        add(btnGirar);

        add(panelInfo);
        add(panelInfo2);

        // Mostramos la ventana
        pack();
        setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        String tipo = "";
        if (((JButton) fuente).getText().equals("Girar")) {
            // Si se ha pulsado el botón de girar, generamos el número ganador y comprobamos
            // las apuestas
            int numeroGanador = (int) (Math.random() * NUM_BOTONES);
            lblUltimoNum.setText("Numero salido: " + numeroGanador);
            lblApuestas.setText("Apuestas: ");
            comprobarApuestas(numeroGanador);
            // reinicio de la lista de apuestas
            listaApuestas.clear();

        } else {
            // comprobamos si la apuesta ya esta en la lista si lo esta sumamos 1€ a la
            // apuesta si no la añadimos
            btnGirar.setVisible(true);
            // Si se ha pulsado un botón de número o opción, añadimos una apuesta
            String opcion = ((JButton) fuente).getText();
            if (opcion.equals("Par") || opcion.equals("Impar") || opcion.equals("Rojo") || opcion.equals("Negro")
                    || opcion.equals("1ª mitad") || opcion.equals("2ª mitad")) {
                // Si se trata de un botón de opción, añadimos una apuesta de 1€
                if (saldo >= MONTO_APUESTA) {
                    saldo -= MONTO_APUESTA;
                    lblSaldo.setText("Saldo: " + saldo + "€");
                } else { // Si no hay saldo suficiente, no se añade la apuesta ymostramos un mensaje
                    // de error
                    JOptionPane.showMessageDialog(this, "No hay saldo suficiente para realizar la apuesta", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    // no se añade la apuesta
                    return;

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
                // Si se trata de un botón de número, añadimos una apuesta de 1€ al número
                // correspondiente
                tipo = "numero";
                if (saldo >= MONTO_APUESTA) {
                    saldo -= MONTO_APUESTA;
                    lblSaldo.setText("Saldo: " + saldo + "€");

                } else { // Si no hay saldo suficiente, no se añade la apuesta ymostramos un mensaje
                    // de error
                    JOptionPane.showMessageDialog(this, "No hay saldo suficiente para realizar la apuesta", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    // no se añade la apuesta
                    return;

                }

            }
            boolean repetida = false;
            int posicion = 0;
            if (listaApuestas.isEmpty()) {
                listaApuestas.add(new Apuesta(tipo, opcion, 1));
            } else {
                for (int i = 0; i < listaApuestas.size(); i++) {
                    if (listaApuestas.get(i).getTipo().equals(tipo)
                            && listaApuestas.get(i).getOpcion().equals(opcion)) {
                                repetida = true;
                                posicion = i;
                        break;
                    } 
                }
                if(repetida == false){
                    listaApuestas.add(new Apuesta(tipo, opcion, 1));
                }else if (repetida == true){
                    listaApuestas.get(posicion).setMonto(listaApuestas.get(posicion).getMonto() + 1);
                }

            }
            // guardamos las apuestas en una variable de tipo string con saltos de linea en
            // cada apuesta
            String apuestas = "<html>";
            for (Apuesta apuesta : listaApuestas) {

                apuestas += "<br>" + apuesta + "";
            }
            apuestas += "</html>";
            // mostramos las apuestas en el label
            // el label va ha mostrar las apuestas en una sola linea, por lo que hay que
            // poner los saltos de linea
            // en el label
            lblApuestas.setText("<html>Apuestas:<br>" + apuestas + "</html>");

        }
    }

    private void comprobarApuestas(int numeroGanador) {
        // generamos dos listas una con los numeros rojos y otra con los nuimeros negros
        int[] numerosRojos = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36 };
        int[] numerosNegros = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35 };

        for (Apuesta apuesta : listaApuestas) {
            String tipo = apuesta.getTipo();
            String opcion = apuesta.getOpcion();
            int monto = apuesta.getMonto();
            int beneficio = 0; // Beneficio de la apuesta (si es ganadora)
            switch (tipo) {
                case "numero":
                    // Si la apuesta es a un número, comprobamos si coincide con el número ganador
                    if (opcion.equals(String.valueOf(numeroGanador))) {
                        // Si coincide, la apuesta es ganadora y calculamos el beneficio
                        beneficio = monto * 36;
                    }
                    break;
                case "parimpar":
                    // comprobamos que la opcion sea par e impar y que el numero sea par o impar

                    if (opcion.equals("Par") && numeroGanador % 2 == 0) {
                        beneficio = monto * 2;
                    } else if (opcion.equals("Impar") && numeroGanador % 2 != 0) {
                        beneficio = monto * 2;
                    }
                    break;
                case "color":
                    // comprobamos que la opcion sea rojo y que el numero sea igual al color
                    for (int i = 0; i < numerosRojos.length; i++) {
                        if (opcion.equals("Rojo") && numerosRojos[i] == numeroGanador) {
                            beneficio = monto * 2;
                        } else if (opcion.equals("Negro") && numerosNegros[i] == numeroGanador) {
                            beneficio = monto * 2;
                        }
                    }
                    break;
                case "mitad":

                    // Si la apuesta es a la primera o segunda mitad, comprobamos a cuál pertenece
                    if (opcion.equals("1ª mitad") && numeroGanador <= 18) {
                        beneficio = monto * 2;
                    } else if (opcion.equals("2ª mitad") && numeroGanador > 18) {
                        beneficio = monto * 2;
                    }
                    break;

            }

            // Añadimos el beneficio al saldo actual
            saldo += beneficio;

        }
        // Mostramos el saldo actual
        lblSaldo.setText("Saldo: " + saldo + "€");
    }

    public static void main(String[] args) {
        new Login();

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

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public void setOpcion(String opcion) {
            this.opcion = opcion;
        }

        public void setMonto(int monto) {
            this.monto = monto;
        }

        public String toString() {
            return monto + "€ al " + tipo + " " + opcion;
        }
    }

}

//vamos a crear un panel para hacer un login

