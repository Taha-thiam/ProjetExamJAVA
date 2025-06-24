package sn.Fama_Taha.view;

import javax.swing.*;
import sn.Fama_Taha.exception.AdministrateurNotFoundException;
import sn.Fama_Taha.entity.Administrateur;
import sn.Fama_Taha.repository.AuthRepository;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorMessage;
    private final AuthRepository authRepository = new AuthRepository();

    public LoginView() {
        setTitle("Connexion Administrateur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal avec padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(245, 245, 250));

        // Titre
        JLabel title = new JLabel("Connexion", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));
        mainPanel.add(title, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginLabel = new JLabel("Login :");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(loginLabel, gbc);

        loginField = new JTextField();
        loginField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(loginField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        // Bouton Se connecter
        loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Message d'erreur
        errorMessage = new JLabel("", SwingConstants.CENTER);
        errorMessage.setFont(new Font("Arial", Font.PLAIN, 13));
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(false);
        mainPanel.add(errorMessage, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        loginButton.addActionListener(_ -> SeConnecter());
    }

   // ...existing code...
private void SeConnecter() {
    String login = loginField.getText();
    String password = new String(passwordField.getPassword());
    try {
        Administrateur administrateur = authRepository.login(login, password);
        errorMessage.setText("Bienvenue, " + administrateur.getLogin() + " !");
        errorMessage.setForeground(new Color(39, 174, 96));
        errorMessage.setVisible(true);

        // Ouvrir la fenêtre principale et fermer la fenêtre de login
        SwingUtilities.invokeLater(() -> {
            new LayoutAvecMenuExemple(); // Affiche la fenêtre principale
            this.dispose(); // Ferme la fenêtre de login
        });

    } catch (AdministrateurNotFoundException e) {
        errorMessage.setText(e.getMessage());
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(true);
    } catch (Exception e) {
        errorMessage.setText("Erreur technique : " + e.getMessage());
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(true);
    }
}
// ...existing code...
}