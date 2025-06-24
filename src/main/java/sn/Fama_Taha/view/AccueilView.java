package sn.Fama_Taha.view;
import javax.swing.*;
import java.awt.*;



public class AccueilView extends JPanel {
    public AccueilView(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));

        JLabel label = new JLabel("<html><center><h2>Bienvenue !</h2><br/>Vous êtes connecté à la bibliothèque.</center></html>", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        label.setForeground(new Color(44, 62, 80));
        add(label, BorderLayout.CENTER);

        JButton btnDeconnexion = new JButton("Se déconnecter");
        btnDeconnexion.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnDeconnexion.setBackground(new Color(231, 76, 60));
        btnDeconnexion.setForeground(Color.WHITE);
        btnDeconnexion.setFocusPainted(false);
        btnDeconnexion.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnDeconnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeconnexion.addActionListener(_ -> {
            // Fermer la fenêtre principale
            if (parentFrame != null) parentFrame.dispose();
            // Afficher la page de connexion
            SwingUtilities.invokeLater(LoginView::new);
        });

        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(new Color(236, 240, 241));
        panelBtn.add(btnDeconnexion);
        add(panelBtn, BorderLayout.SOUTH);
    }
}
