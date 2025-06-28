package sn.Fama_Taha.view;

import javax.swing.*;
import java.awt.*;

public class LayoutAvecMenuExemple extends JFrame {

    public LayoutAvecMenuExemple() {
        setTitle("Gestion de Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        Font fontTitre = new Font("Segoe UI", Font.BOLD, 28);
        Font fontMenu = new Font("Segoe UI", Font.PLAIN, 16);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(44, 62, 80));
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Menu Accueil
        JMenu menuAccueil = new JMenu("Accueil");
        menuAccueil.setForeground(Color.WHITE);
        menuAccueil.setFont(fontMenu);
        JMenuItem itemAccueil = new JMenuItem("Page d'accueil");
        menuAccueil.add(itemAccueil);

        // Menu Membres
        JMenu menuMembre = new JMenu("Membres");
        menuMembre.setForeground(Color.WHITE);
        menuMembre.setFont(fontMenu);
        JMenuItem itemListerMembres = new JMenuItem("Lister les membres");
        JMenuItem itemAjouterMembre = new JMenuItem("Ajouter un membre");
        menuMembre.add(itemListerMembres);
        menuMembre.add(itemAjouterMembre);

        // Menu Ouvrages
        JMenu menuOuvrage = new JMenu("Ouvrages");
        menuOuvrage.setForeground(Color.WHITE);
        menuOuvrage.setFont(fontMenu);
        JMenuItem itemListerOuvrages = new JMenuItem("Lister les ouvrages");
        JMenuItem itemAjouterOuvrage = new JMenuItem("Ajouter un ouvrage");
        menuOuvrage.add(itemListerOuvrages);
        menuOuvrage.add(itemAjouterOuvrage);

        // Menu Emprunts
        JMenu menuEmprunt = new JMenu("Emprunts");
        menuEmprunt.setForeground(Color.WHITE);
        menuEmprunt.setFont(fontMenu);
        JMenuItem itemListerEmprunts = new JMenuItem("Lister les emprunts");
        menuEmprunt.add(itemListerEmprunts);

        // Menu Réservations
        JMenu menuReservation = new JMenu("Réservations");
        menuReservation.setForeground(Color.WHITE);
        menuReservation.setFont(fontMenu);
        JMenuItem itemListerReservations = new JMenuItem("Lister les réservations");
        menuReservation.add(itemListerReservations);
        // Menu Amendes
        JMenu menuAmende = new JMenu("Amendes");
        menuAmende.setForeground(Color.WHITE);
        menuAmende.setFont(fontMenu);
        JMenuItem itemListerAmendes = new JMenuItem("Lister les amendes");
        menuAmende.add(itemListerAmendes);

        // Ajout des menus à la barre
        menuBar.add(menuAccueil); // Ajout du menu Accueil en premier
        menuBar.add(menuMembre);
        menuBar.add(menuOuvrage);
        menuBar.add(menuEmprunt);
        menuBar.add(menuReservation);
        menuBar.add(menuAmende);

        setJMenuBar(menuBar);

        // Panel principal avec fond
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));
        add(mainPanel);

        // En-tête stylisé
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel titre = new JLabel("Bienvenue dans la Gestion de Bibliothèque");
        titre.setForeground(Color.WHITE);
        titre.setFont(fontTitre);
        headerPanel.add(titre);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de contenu (pour changer selon le menu)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Actions des menus
        itemAccueil.addActionListener(_ -> {
            contentPanel.removeAll();
            contentPanel.add(new AccueilView(this), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        itemListerMembres.addActionListener(_ -> {
            contentPanel.removeAll();
            contentPanel.add(new MembreView(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        itemListerOuvrages.addActionListener(_ -> {
            contentPanel.removeAll();
            contentPanel.add(new OuvrageView(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        itemListerEmprunts.addActionListener(_ -> {
            contentPanel.removeAll();
            contentPanel.add(new EmpruntView(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        // Action du menu Amendes
        itemListerAmendes.addActionListener(_ -> {
            contentPanel.removeAll();
            contentPanel.add(new AmendeView(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        itemListerReservations.addActionListener(_ -> {
            contentPanel.removeAll();
            contentPanel.add(new ReservationView(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        // Ajoutez les autres actions ici...

        // Affichage de la page d'accueil par défaut au lancement
        contentPanel.add(new AccueilView(this), BorderLayout.CENTER);

        setVisible(true);
    }
}