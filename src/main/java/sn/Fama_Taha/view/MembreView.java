package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Membre;
import sn.Fama_Taha.repository.MembreRepository;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class MembreView extends JPanel {
    private JTable membreTable;
    private DefaultTableModel tableModel;
    private MembreRepository membreRepository = new MembreRepository();

    public MembreView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Colonnes du tableau
        String[] columns = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Type"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        membreTable = new JTable(tableModel);

        // Style du tableau
        membreTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        membreTable.setRowHeight(28);
        membreTable.setSelectionBackground(new Color(102, 178, 255));
        membreTable.setSelectionForeground(Color.BLACK);

        // En-tête stylisé
        JTableHeader header = membreTable.getTableHeader();
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Rendu alterné des lignes
        membreTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(230, 240, 255) : Color.WHITE);
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        // Chargement des membres
        loadMembres();

        JScrollPane scrollPane = new JScrollPane(membreTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Boutons stylisés
        JButton addButton = createButton("Ajouter un membre", new Color(46, 204, 113));
        JButton editButton = createButton("Modifier", new Color(241, 196, 15));
        JButton deleteButton = createButton("Supprimer", new Color(231, 76, 60));

        addButton.addActionListener(_ -> ajouterMembre());
        editButton.addActionListener(_ -> modifierMembre());
        deleteButton.addActionListener(_ -> supprimerMembre());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Titre
        JLabel title = new JLabel("Gestion des Membres");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
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
