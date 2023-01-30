
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

    private JLabel lblUsuario, lblPassword, lblInforma;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnAceptar, btnCancelar;

    /**
     * 
     */
    public Login() {
        super("Login");
        setLayout(new FlowLayout());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        lblUsuario = new JLabel("Usuario:");
        lblPassword = new JLabel("Password:");
        txtUsuario = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        
        lblInforma = new JLabel("Usuario: admin | Password: admin");

        add(lblUsuario);
        add(txtUsuario);
        add(lblPassword);
        add(txtPassword);
        add(btnAceptar);
        add(btnCancelar);
        add(lblInforma);

        btnAceptar.addActionListener(this);
        btnCancelar.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAceptar) {
            String usuario = txtUsuario.getText();
            String password = txtPassword.getText();
            if (usuario.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + usuario);
                dispose();
                new RuletaCasino();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        } else if (e.getSource() == btnCancelar) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}