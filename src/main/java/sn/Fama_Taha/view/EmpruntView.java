package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Emprunt;
import sn.Fama_Taha.entity.Membre;
import sn.Fama_Taha.entity.Ouvrage;
import sn.Fama_Taha.repository.EmpruntRepository;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmpruntView extends JPanel {
    private JTable empruntTable;
    private DefaultTableModel tableModel;
    private EmpruntRepository empruntRepository = new EmpruntRepository();

    public EmpruntView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Ajout de la colonne "Retour"
        String[] columns = { "ID", "Date Emprunt", "Retour Prévue", "Membre", "Ouvrage", "Retour" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                // Seule la colonne bouton est éditable
                return column == 5;
            }
        };
        empruntTable = new JTable(tableModel);

        empruntTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        empruntTable.setRowHeight(28);
        empruntTable.setSelectionBackground(new Color(102, 178, 255));
        empruntTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = empruntTable.getTableHeader();
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        empruntTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                if (column == 5) return null; // handled by button renderer
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(230, 240, 255) : Color.WHITE);
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        // Ajout du renderer et editor pour le bouton
        empruntTable.getColumn("Retour").setCellRenderer(new ButtonRenderer());
        empruntTable.getColumn("Retour").setCellEditor(new ButtonEditor(new JCheckBox()));

        loadEmprunts();

        JScrollPane scrollPane = new JScrollPane(empruntTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = createButton("Ajouter un emprunt", new Color(46, 204, 113));
        JButton editButton = createButton("Modifier", new Color(241, 196, 15));
        JButton deleteButton = createButton("Supprimer", new Color(231, 76, 60));

        addButton.addActionListener(_ -> ajouterEmprunt());
        editButton.addActionListener(_ -> modifierEmprunt());
        deleteButton.addActionListener(_ -> supprimerEmprunt());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        JLabel title = new JLabel("Gestion des Emprunts");
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

    private void loadEmprunts() {
        tableModel.setRowCount(0);
        List<Emprunt> emprunts = empruntRepository.findAll();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Emprunt e : emprunts) {
            tableModel.addRow(new Object[] {
                    e.getIdEmprunt(),
                    e.getDateEmprunt() != null ? e.getDateEmprunt().format(fmt) : "",
                    e.getDateRetourPrevue() != null ? e.getDateRetourPrevue().format(fmt) : "",
                    e.getMembre() != null ? e.getMembre().getNom() + " " + e.getMembre().getPrenom() : "",
                    e.getOuvrage() != null ? e.getOuvrage().getTitre() : "",
                    e.isRetourEmprunt() ? "Rendu" : "Non rendu"
            });
        }
    }

    private void ajouterEmprunt() {
        JTextField idField = new JTextField();
        JTextField dateEmpruntField = new JTextField();
        JTextField dateRetourPrevueField = new JTextField();

        List<Membre> membres = new sn.Fama_Taha.repository.MembreRepository().findAll();
        JComboBox<Membre> membreBox = new JComboBox<>(membres.toArray(new Membre[0]));
        membreBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Membre) {
                    Membre m = (Membre) value;
                    setText(m.getNom() + " " + m.getPrenom());
                }
                return this;
            }
        });

        // Filtrer les ouvrages disponibles uniquement
        List<Ouvrage> ouvrages = new sn.Fama_Taha.repository.OuvrageRepository().findAll();
        ouvrages.removeIf(o -> !o.isDisponible());
        JComboBox<Ouvrage> ouvrageBox = new JComboBox<>(ouvrages.toArray(new Ouvrage[0]));
        ouvrageBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Ouvrage) {
                    Ouvrage o = (Ouvrage) value;
                    setText(o.getTitre());
                }
                return this;
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("ID :"));
        panel.add(idField);
        panel.add(new JLabel("Date emprunt (yyyy-MM-dd) :"));
        panel.add(dateEmpruntField);
        panel.add(new JLabel("Date retour prévue (yyyy-MM-dd) :"));
        panel.add(dateRetourPrevueField);
        panel.add(new JLabel("Membre :"));
        panel.add(membreBox);
        panel.add(new JLabel("Ouvrage :"));
        panel.add(ouvrageBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter un emprunt", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Emprunt emprunt = new Emprunt();
                emprunt.setIdEmprunt(idField.getText());
                emprunt.setDateEmprunt(java.time.LocalDate.parse(dateEmpruntField.getText()));
                emprunt.setDateRetourPrevue(java.time.LocalDate.parse(dateRetourPrevueField.getText()));
                emprunt.setMembre((Membre) membreBox.getSelectedItem());
                emprunt.setOuvrage((Ouvrage) ouvrageBox.getSelectedItem());
                emprunt.setRetourEmprunt(false); // Par défaut à false
                empruntRepository.save(emprunt);
                loadEmprunts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierEmprunt() {
        int selectedRow = empruntTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt à modifier.");
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();
        Emprunt emprunt = empruntRepository.findById(id);
        if (emprunt == null) {
            JOptionPane.showMessageDialog(this, "Emprunt introuvable.");
            return;
        }

        JTextField dateEmpruntField = new JTextField(
                emprunt.getDateEmprunt() != null ? emprunt.getDateEmprunt().toString() : "");
        JTextField dateRetourPrevueField = new JTextField(
                emprunt.getDateRetourPrevue() != null ? emprunt.getDateRetourPrevue().toString() : "");

        List<Membre> membres = new sn.Fama_Taha.repository.MembreRepository().findAll();
        JComboBox<Membre> membreBox = new JComboBox<>(membres.toArray(new Membre[0]));
        membreBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Membre) {
                    Membre m = (Membre) value;
                    setText(m.getNom() + " " + m.getPrenom());
                }
                return this;
            }
        });
        if (emprunt.getMembre() != null)
            membreBox.setSelectedItem(emprunt.getMembre());

        List<Ouvrage> ouvrages = new sn.Fama_Taha.repository.OuvrageRepository().findAll();
        ouvrages.removeIf(o -> !o.isDisponible() && (emprunt.getOuvrage() == null || !o.equals(emprunt.getOuvrage())));
        JComboBox<Ouvrage> ouvrageBox = new JComboBox<>(ouvrages.toArray(new Ouvrage[0]));
        ouvrageBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Ouvrage) {
                    Ouvrage o = (Ouvrage) value;
                    setText(o.getTitre());
                }
                return this;
            }
        });
        if (emprunt.getOuvrage() != null)
            ouvrageBox.setSelectedItem(emprunt.getOuvrage());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Date emprunt (yyyy-MM-dd) :"));
        panel.add(dateEmpruntField);
        panel.add(new JLabel("Date retour prévue (yyyy-MM-dd) :"));
        panel.add(dateRetourPrevueField);
        panel.add(new JLabel("Membre :"));
        panel.add(membreBox);
        panel.add(new JLabel("Ouvrage :"));
        panel.add(ouvrageBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modifier l'emprunt", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                emprunt.setDateEmprunt(java.time.LocalDate.parse(dateEmpruntField.getText()));
                emprunt.setDateRetourPrevue(java.time.LocalDate.parse(dateRetourPrevueField.getText()));
                emprunt.setMembre((Membre) membreBox.getSelectedItem());
                emprunt.setOuvrage((Ouvrage) ouvrageBox.getSelectedItem());
                empruntRepository.update(emprunt);
                loadEmprunts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification : " + ex.getMessage(), "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerEmprunt() {
        int selectedRow = empruntTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt à supprimer.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cet emprunt ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object idObj = tableModel.getValueAt(selectedRow, 0);
            if (idObj != null) {
                empruntRepository.delete(empruntRepository.findById(idObj.toString()));
                loadEmprunts();
            }
        }
    }

    // Renderer pour le bouton dans la colonne "Retour"
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null && value.equals("Rendu") ? "Annuler retour" : "Valider retour");
            setBackground(value != null && value.equals("Rendu") ? new Color(241, 196, 15) : new Color(46, 204, 113));
            setForeground(Color.WHITE);
            return this;
        }
    }

    // Editor pour le bouton dans la colonne "Retour"
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    String id = tableModel.getValueAt(selectedRow, 0).toString();
                    Emprunt emprunt = empruntRepository.findById(id);
                    if (emprunt != null) {
                        emprunt.setRetourEmprunt(!emprunt.isRetourEmprunt());
                        empruntRepository.update(emprunt);
                        loadEmprunts();
                    }
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            selectedRow = row;
            label = value != null && value.equals("Rendu") ? "Annuler retour" : "Valider retour";
            button.setText(label);
            button.setBackground(value != null && value.equals("Rendu") ? new Color(241, 196, 15) : new Color(46, 204, 113));
            button.setForeground(Color.WHITE);
            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }
    }
}