package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Membre;
import sn.Fama_Taha.repository.MembreRepository;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MembreView extends JPanel {
    private void ajouterMembre() {
        JTextField idField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telephoneField = new JTextField();
        JTextField typeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("ID :"));
        panel.add(idField);
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Téléphone :"));
        panel.add(telephoneField);
        panel.add(new JLabel("Type :"));
        panel.add(typeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter un membre", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || nomField.getText().isEmpty() || prenomField.getText().isEmpty()
                    || emailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sauf téléphone sont obligatoires.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Membre membre = new Membre();
            membre.setIdMembre(idField.getText());
            membre.setNom(nomField.getText());
            membre.setPrenom(prenomField.getText());
            membre.setEmail(emailField.getText());
            membre.setTelephone(telephoneField.getText());
            membre.setTypeMembre(typeField.getText());
            membreRepository.save(membre);
            loadMembres();
        }
    }

    private JTable membreTable;
    private DefaultTableModel tableModel;
    private MembreRepository membreRepository = new MembreRepository();

    public MembreView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Colonnes du tableau
        String[] columns = { "ID", "Nom", "Prénom", "Email", "Téléphone", "Type", "Modifier", "Supprimer" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                // Seules les colonnes "Modifier" et "Supprimer" sont éditables (boutons)
                return column == 6 || column == 7;
            }
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
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Rendu alterné des lignes
        membreTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(230, 240, 255) : Color.WHITE);
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        // Ajout des renderers et editors pour les boutons
        membreTable.getColumn("Modifier").setCellRenderer(new ButtonRenderer("Modifier", new Color(241, 196, 15)));
        membreTable.getColumn("Modifier").setCellEditor(new ButtonEditor(new JCheckBox(), "Modifier"));
        membreTable.getColumn("Supprimer").setCellRenderer(new ButtonRenderer("Supprimer", new Color(231, 76, 60)));
        membreTable.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), "Supprimer"));

        // Chargement des membres
        loadMembres();

        JScrollPane scrollPane = new JScrollPane(membreTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Titre
        JLabel title = new JLabel("Gestion des Membres");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);
        // ... après l'ajout du titre ...
        JButton addButton = new JButton("Ajouter un membre");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Action du bouton
        addButton.addActionListener(_ -> ajouterMembre());

        // Ajout du bouton en haut (sous le titre)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 250));
        topPanel.add(addButton);
        add(topPanel, BorderLayout.NORTH);

        // Si tu veux le bouton en bas, utilise BorderLayout.SOUTH à la place
    }

    private void loadMembres() {
        tableModel.setRowCount(0); // Clear
        List<Membre> membres = membreRepository.findAll();
        for (Membre m : membres) {
            tableModel.addRow(new Object[] {
                    m.getIdMembre(),
                    m.getNom(),
                    m.getPrenom(),
                    m.getEmail(),
                    m.getTelephone(),
                    m.getTypeMembre(),
                    "Modifier",
                    "Supprimer"
            });
        }
    }

    // Renderer pour les boutons
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text, Color color) {
            setText(text);
            setOpaque(true);
            setBackground(color);
            setForeground(Color.WHITE);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor pour les boutons
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String actionType;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
            button = new JButton(actionType);
            button.setOpaque(true);
            if ("Modifier".equals(actionType)) {
                button.setBackground(new Color(241, 196, 15));
            } else {
                button.setBackground(new Color(231, 76, 60));
            }
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    selectedRow = membreTable.getSelectedRow();
                    if ("Modifier".equals(actionType)) {
                        modifierMembre(selectedRow);
                    } else if ("Supprimer".equals(actionType)) {
                        supprimerMembre(selectedRow);
                    }
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            selectedRow = row;
            return button;
        }

        public Object getCellEditorValue() {
            return actionType;
        }
    }

    // Adaptation de modifierMembre pour recevoir l'index
    private void modifierMembre(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre à modifier.");
            return;
        }
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String nom = tableModel.getValueAt(selectedRow, 1).toString();
        String prenom = tableModel.getValueAt(selectedRow, 2).toString();
        String email = tableModel.getValueAt(selectedRow, 3).toString();
        String telephone = tableModel.getValueAt(selectedRow, 4).toString();
        String type = tableModel.getValueAt(selectedRow, 5).toString();

        JTextField nomField = new JTextField(nom);
        JTextField prenomField = new JTextField(prenom);
        JTextField emailField = new JTextField(email);
        JTextField telephoneField = new JTextField(telephone);
        JTextField typeField = new JTextField(type);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Téléphone :"));
        panel.add(telephoneField);
        panel.add(new JLabel("Type :"));
        panel.add(typeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modifier le membre", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Membre membre = membreRepository.findById(id);
            membre.setNom(nomField.getText());
            membre.setPrenom(prenomField.getText());
            membre.setEmail(emailField.getText());
            membre.setTelephone(telephoneField.getText());
            membre.setTypeMembre(typeField.getText());
            membreRepository.update(membre);
            loadMembres();
        }
    }

    // Adaptation de supprimerMembre pour recevoir l'index
    private void supprimerMembre(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un membre à supprimer.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer ce membre ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object idObj = tableModel.getValueAt(selectedRow, 0);
            if (idObj != null) {
                String id = idObj.toString();
                membreRepository.delete(membreRepository.findById(id));
                loadMembres();
            }
        }
    }
}