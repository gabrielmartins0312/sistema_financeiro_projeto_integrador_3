package view;

import javax.swing.*;

import controller.LoginController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JLabel messageLabel;
    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();

        setTitle("Tela de Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        panel.add(senhaField);

        loginButton = new JButton("Login");
        panel.add(loginButton);

        messageLabel = new JLabel();
        panel.add(messageLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String senha = new String(senhaField.getPassword());

                if (loginController.autenticar(nome, senha)) {
                    messageLabel.setText("Login bem-sucedido!");
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new CadastroView(nome).setVisible(true);
                            dispose();
                        }
                    });
                } else {
                    messageLabel.setText("Nome ou senha inválidos.");
                }
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
}
