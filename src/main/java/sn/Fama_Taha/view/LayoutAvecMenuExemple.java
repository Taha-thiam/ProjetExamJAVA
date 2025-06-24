package sn.Fama_Taha.view;

import javax.swing.*;
import java.awt.*;

public class LayoutAvecMenuExemple extends JFrame {

    public LayoutAvecMenuExemple() {
        setTitle("Gestion de Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Barre de menu
        JMenuBar menuBar = new JMenuBar();

        // Menu Membres
        JMenu menuMembre = new JMenu("Membres");
        JMenuItem itemListerMembres = new JMenuItem("Lister les membres");
        JMenuItem itemAjouterMembre = new JMenuItem("Ajouter un membre");
        menuMembre.add(itemListerMembres);
        menuMembre.add(itemAjouterMembre);

        // Menu Ouvrages
        JMenu menuOuvrage = new JMenu("Ouvrages");
        JMenuItem itemListerOuvrages = new JMenuItem("Lister les ouvrages");
        JMenuItem itemAjouterOuvrage = new JMenuItem("Ajouter un ouvrage");
        menuOuvrage.add(itemListerOuvrages);
        menuOuvrage.add(itemAjouterOuvrage);

        // Menu Emprunts
        JMenu menuEmprunt = new JMenu("Emprunts");
        JMenuItem itemListerEmprunts = new JMenuItem("Lister les emprunts");
        menuEmprunt.add(itemListerEmprunts);

        // Menu Réservations
        JMenu menuReservation = new JMenu("Réservations");
        JMenuItem itemListerReservations = new JMenuItem("Lister les réservations");
        menuReservation.add(itemListerReservations);

        // Ajout des menus à la barre
        menuBar.add(menuMembre);
        menuBar.add(menuOuvrage);
        menuBar.add(menuEmprunt);
        menuBar.add(menuReservation);

        setJMenuBar(menuBar);

        // Panel principal (pour afficher les vues selon le menu)
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        itemListerMembres.addActionListener(_ -> {
            mainPanel.removeAll();
            mainPanel.add(new MembreView(), BorderLayout.CENTER); // <-- AJOUTE CETTE LIGNE
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        itemListerOuvrages.addActionListener(_ -> {
            mainPanel.removeAll();
            mainPanel.add(new OuvrageView(), BorderLayout.CENTER); // Affiche la vue des ouvrages
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // ... Ajouter d'autres actions selon les besoins

        setVisible(true);
    }
}
