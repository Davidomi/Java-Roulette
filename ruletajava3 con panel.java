import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CasinoRoulette extends JFrame implements ActionListener {
  private static final int MIN_BET = 5;
  private static final int MAX_BET = 100;
  private static final int NUM_SLOTS = 37; // incluye el 0

  private static int balance = 1000; // saldo inicial
  private static int betAmount;
  private static int betType;
  private static int winningSlot;

  private JLabel balanceLabel;
  private JTextField betField;
  private JButton redButton;
  private JButton blackButton;
  private JButton evenButton;
  private JButton oddButton;
  private JLabel resultLabel;

  public CasinoRoulette() {
    super("Ruleta de Casino");

    // panel de saldo
    balanceLabel = new JLabel("Saldo: " + balance);
    JPanel balancePanel = new JPanel();
    balancePanel.add(balanceLabel);

    // panel de apuesta
    JLabel betLabel = new JLabel("Apuesta: ");
    betField = new JTextField(5);
    JPanel betPanel = new JPanel();
    betPanel.add(betLabel);
    betPanel.add(betField);

    // panel de botones
    redButton = new JButton("Rojo");
    blackButton = new JButton("Negro");
    evenButton = new JButton("Par");
    oddButton = new JButton("Impar");
    JPanel buttonsPanel = new JPanel(new FlowLayout());
    buttonsPanel.add(redButton);
    buttonsPanel.add(blackButton);
    buttonsPanel.add(evenButton);
    buttonsPanel.add(oddButton);

    // panel de resultado
    resultLabel = new JLabel("");
    JPanel resultPanel = new JPanel();
    resultPanel.add(resultLabel);

    // agrega todos los paneles al marco principal
    add(balancePanel, BorderLayout.NORTH);
    add(betPanel, BorderLayout.WEST);
    add(buttonsPanel, BorderLayout.CENTER);
    add(resultPanel, BorderLayout.SOUTH);

    // registra los botones para recibir eventos de acción
    redButton.addActionListener(this);
    blackButton.addActionListener(this);
    evenButton.addActionListener(this);
    oddButton.addActionListener(this);
  }

  public static void main(String[] args) {
    CasinoRoulette roulette = new CasinoRoulette();
    roulette.setDefaultClose Operación, JFrame.EXIT_ON_CLOSE);
roulette.setPreferredSize(new Dimension(300, 200));
roulette.pack();
roulette.setLocationRelativeTo(null);
roulette.setVisible(true);
}

@Override
public void actionPerformed(ActionEvent e) {
betAmount = Integer.parseInt(betField.getText());

if (betAmount < MIN_BET) {
  resultLabel.setText("La apuesta mínima es de " + MIN_BET);
  return;
}

if (betAmount > balance) {
  resultLabel.setText("No tienes suficiente saldo para esa apuesta");
  return;
}

if (e.getSource() == redButton) {
  betType = 1;
} else if (e.getSource() == blackButton) {
  betType = 2;
} else if (e.getSource() == evenButton) {
  betType = 3;
} else if (e.getSource() == oddButton) {
  betType = 4;
}

// gira la ruleta
Random random = new Random();
winningSlot = random.nextInt(NUM_SLOTS);

// comprueba si ganó o perdió
if (checkWin()) {
  balance += betAmount;
  balanceLabel.setText("Saldo: " + balance);
  resultLabel.setText("¡Felicidades, has ganado! La bola cayó en el slot " + winningSlot);
} else {
  balance -= betAmount;
  balanceLabel.setText("Saldo: " + balance);
  resultLabel.setText("Lo siento, has perdido. La bola cayó en el slot " + winningSlot);
}

if (balance == 0) {
  resultLabel.setText("Lo siento, has quedado sin saldo. ¡Vuelve pronto!");
  redButton.setEnabled(false);
  blackButton.setEnabled(false);
  evenButton.setEnabled(false);
  oddButton.setEnabled(false);
}
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
|| winningSlot == 12 || winningSlot == 14 || winningSlot == 16 || winningSlot == 18 || winningSlot == 19
|| winningSlot == 21 || winningSlot == 23 || winningSlot == 25 || winningSlot == 27 || winningSlot == 30
|| winningSlot == 32 || winningSlot == 34 || winningSlot == 36;
} 
}

