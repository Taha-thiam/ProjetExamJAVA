package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Membre;
import sn.Fama_Taha.repository.MembreRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MembreView extends JPanel {
    private JTable membreTable;
    private DefaultTableModel tableModel;
    private MembreRepository membreRepository = new MembreRepository();

    public MembreView() {
        setLayout(new BorderLayout());

        // Colonnes du tableau (sans "Créateur")
        String[] columns = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Type"};
        tableModel = new DefaultTableModel(columns, 0);
        membreTable = new JTable(tableModel);

        // Chargement des membres
        loadMembres();

        JScrollPane scrollPane = new JScrollPane(membreTable);
        add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JButton addButton = new JButton("Ajouter un membre");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        addButton.addActionListener(_ -> ajouterMembre());
        editButton.addActionListener(_ -> modifierMembre());
        deleteButton.addActionListener(_ -> supprimerMembre());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadMembres() {
        tableModel.setRowCount(0); // Clear
        List<Membre> membres = membreRepository.findAll();
        for (Membre m : membres) {
            tableModel.addRow(new Object[]{
                m.getIdMembre(),
                m.getNom(),
                m.getPrenom(),
                m.getEmail(),
                m.getTelephone(),
                m.getTypeMembre()
            });
        }
    }

    private void ajouterMembre() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité d'ajout à implémenter.");
    }

    private void modifierMembre() {
        int selectedRow = membreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre à modifier.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Fonctionnalité de modification à implémenter.");
    }

    private void supprimerMembre() {
        int selectedRow = membreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre à supprimer.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer ce membre ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object idObj = tableModel.getValueAt(selectedRow, 0);
            if (idObj != null) {
                Long id = Long.valueOf(idObj.toString());
                membreRepository.delete(membreRepository.findById(id.toString()));
                loadMembres();
            }
        }
    }
}
