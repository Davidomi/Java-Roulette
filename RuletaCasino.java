# muestra el codigo en java para crear  una ruleta de casino con las siguientes caracteristicas:

# los numeros van ha ser botones donde cada vez que pulsemos se añadira una apuesta de 1 euro a ese numero, en caso de acierto el veneficio sera de un x36
# se mostrara en el mismo panel un boton para apostar al numero par o impar, en caso de acierto el veneficio sera de un x2
# otros para apostar entre rojo o negro, en caso de acierto el veneficio sera de un x2
# otros para primera o segunda mitad , en caso de acierto el veneficio sera de un x2

# tu saldo inicial es de 10 euros y si tu apuesta es mayor a tu saldo no nos dejara apostar

# se pueden hacer mas de una apuesta a la vez
# si ganas o pierdes te muetra el resultado en el mismo panel con el numero que ha salido

# la apuesta se ira recopilando y cuando se pulse el boton de girar la ruleta se mostrara el numero ganador y se mostrara el dinero ganado o perdido.

    import java.awt.FlowLayout;
    import java.awt.GridLayout;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.List;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    
    public class RuletaCasino extends JFrame implements ActionListener {
    
        private static final int NUM_BOTONES = 36;
        private static final int NUM_OPCIONES = 3;
        private static final int MONTO_APUESTA = 1;
        private List<Apuesta> listaApuestas;
        private int saldo = 10;
        private JLabel lblSaldo;
        private JButton[] btnNumeros = new JButton[NUM_BOTONES];
        private JButton[] btnOpciones = new JButton[NUM_OPCIONES];
        private JButton btnGirar;
    
        public RuletaCasino() {
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
            String tipo = "numero";
            if (fuente == btnGirar) {
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
                    if (opcion.equals("Par") || opcion.equals("Impar")){
                        tipo = "parimpar";
                    } else if (opcion.equals("Rojo") || opcion.equals("Negro")) {
                        tipo = "color";
                    } else {
                        tipo = "mitad";
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
                listaApuestas.add(new Apuesta(tipo, opcion, 1));

            }
        }
    
        private void comprobarApuestas(int numeroGanador) {
            // Recorremos la lista de apuestas y comprobamos si cada una ha sido acertada
            for (Apuesta apuesta : listaApuestas) {
                int beneficio = 0;
                if (apuesta.getTipo().equals("número") && apuesta.getOpcion().equals(numeroGanador)) {
                    // Si se ha apostado al número y coincide con el número ganador, aplicamos el beneficio de 36 veces el monto de la apuesta
                    beneficio = 36 * apuesta.getMonto();
                } else if (apuesta.getTipo().equals("parimpar") && ((numeroGanador % 2 == 0 && apuesta.getOpcion().equals("par")) || (numeroGanador % 2 == 1 && apuesta.getOpcion().equals("impar")))) {
                    // Si se ha apostado a par/impar y coincide con el número ganador, aplicamos el beneficio de 2 veces el monto de la apuesta
                    beneficio = 2 * apuesta.getMonto();
                } else if (apuesta.getTipo().equals("color") && ((List.of(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36).contains(numeroGanador) && apuesta.getOpcion().equals("rojo")) || (List.of(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35).contains(numeroGanador) && apuesta.getOpcion().equals("negro")))) {
                    // Si se ha apostado a rojo/negro y coincide con el número ganador, aplicamos el beneficio de 2 veces el monto de la apuesta
                    beneficio = 2 * apuesta.getMonto();
                } else if (apuesta.getTipo().equals("mitad") && ((numeroGanador >= 1 && numeroGanador <= 18 && apuesta.getOpcion().equals("1ª mitad")) || (numeroGanador >= 19 && numeroGanador <= 36 && apuesta.getOpcion().equals("2ª mitad")))) {
                    // Si se ha apostado a 1ª mitad/2ª mitad y coincide con el número ganador, aplicamos el beneficio de 2 veces el monto de la apuesta
                    beneficio = 2 * apuesta.getMonto();
                }
                // Añadimos el beneficio al saldo actual
                saldo += beneficio;
            }
            // Mostramos el saldo actual
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
    }
    
    }
    