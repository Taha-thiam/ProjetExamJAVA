package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Ouvrage;
import sn.Fama_Taha.repository.OuvrageRepository;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class OuvrageView extends JPanel {
    private void ajouterOuvrage() {
        JTextField idField = new JTextField();
        JTextField titreField = new JTextField();
        JTextField auteurField = new JTextField();
        JTextField anneeField = new JTextField();
        JTextField genreField = new JTextField();
        JCheckBox disponibleBox = new JCheckBox("Disponible", true);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("ID :"));
        panel.add(idField);
        panel.add(new JLabel("Titre :"));
        panel.add(titreField);
        panel.add(new JLabel("Auteur :"));
        panel.add(auteurField);
        panel.add(new JLabel("Année de publication :"));
        panel.add(anneeField);
        panel.add(new JLabel("Genre :"));
        panel.add(genreField);
        panel.add(disponibleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter un ouvrage", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().isEmpty() || titreField.getText().isEmpty() || auteurField.getText().isEmpty()
                    || anneeField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sauf genre sont obligatoires.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Ouvrage ouvrage = new Ouvrage();
                ouvrage.setIdOuvrage(idField.getText());
                ouvrage.setTitre(titreField.getText());
                ouvrage.setAuteur(auteurField.getText());
                ouvrage.setAnneePublication(Integer.parseInt(anneeField.getText()));
                ouvrage.setGenre(genreField.getText());
                ouvrage.setDisponible(disponibleBox.isSelected());
                ouvrageRepository.save(ouvrage);
                loadOuvrages();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JTable ouvrageTable;
    private DefaultTableModel tableModel;
    private OuvrageRepository ouvrageRepository = new OuvrageRepository();

    public OuvrageView() {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        String[] columns = { "ID", "Titre", "Auteur", "Année", "Genre", "Disponible", "Modifier", "Supprimer" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                // Seules les colonnes "Modifier" et "Supprimer" sont éditables (boutons)
                return column == 6 || column == 7;
            }
        };
        ouvrageTable = new JTable(tableModel);
        ouvrageTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        ouvrageTable.setRowHeight(28);
        ouvrageTable.setGridColor(new Color(220, 220, 220));
        ouvrageTable.setSelectionBackground(new Color(102, 178, 255));
        ouvrageTable.setSelectionForeground(Color.BLACK);

        // En-tête du tableau stylisée
        JTableHeader header = ouvrageTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(51, 102, 204));
        header.setForeground(Color.WHITE);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // Cellules alternées colorées
        ouvrageTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(235, 243, 255) : Color.WHITE);
                } else {
                    c.setBackground(new Color(102, 178, 255));
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        // Ajout des renderers et editors pour les boutons
        ouvrageTable.getColumn("Modifier").setCellRenderer(new ButtonRenderer("Modifier", new Color(241, 196, 15)));
        ouvrageTable.getColumn("Modifier").setCellEditor(new ButtonEditor(new JCheckBox(), "Modifier"));
        ouvrageTable.getColumn("Supprimer").setCellRenderer(new ButtonRenderer("Supprimer", new Color(231, 76, 60)));
        ouvrageTable.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), "Supprimer"));

        loadOuvrages();

        JScrollPane scrollPane = new JScrollPane(ouvrageTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

     
        // Création du bouton
        JButton addButton = new JButton("Ajouter un ouvrage");
        addButton.setBackground(new Color(51, 102, 204));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        addButton.addActionListener(_ -> ajouterOuvrage());

        // Création du titre
        JLabel titre = new JLabel("Gestion des Ouvrages");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titre.setForeground(new Color(51, 102, 204));
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        titre.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel vertical pour bouton + titre
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(245, 245, 250));
        topPanel.add(addButton);
        topPanel.add(titre);

        add(topPanel, BorderLayout.NORTH);

        // ...le reste du code...
    }

    private void loadOuvrages() {
        tableModel.setRowCount(0);
        List<Ouvrage> ouvrages = ouvrageRepository.findAll();
        for (Ouvrage o : ouvrages) {
            tableModel.addRow(new Object[] {
                    o.getIdOuvrage(),
                    o.getTitre(),
                    o.getAuteur(),
                    o.getAnneePublication(),
                    o.getGenre(),
                    o.isDisponible() ? "Oui" : "Non",
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
                    selectedRow = ouvrageTable.getSelectedRow();
                    if ("Modifier".equals(actionType)) {
                        modifierOuvrage(selectedRow);
                    } else if ("Supprimer".equals(actionType)) {
                        supprimerOuvrage(selectedRow);
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

    // Adaptation de modifierOuvrage pour recevoir l'index
    private void modifierOuvrage(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ouvrage à modifier.");
            return;
        }
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String titre = tableModel.getValueAt(selectedRow, 1).toString();
        String auteur = tableModel.getValueAt(selectedRow, 2).toString();
        String annee = tableModel.getValueAt(selectedRow, 3).toString();
        String genre = tableModel.getValueAt(selectedRow, 4).toString();
        boolean disponible = "Oui".equals(tableModel.getValueAt(selectedRow, 5).toString());

        JTextField titreField = new JTextField(titre);
        JTextField auteurField = new JTextField(auteur);
        JTextField anneeField = new JTextField(annee);
        JTextField genreField = new JTextField(genre);
        JCheckBox disponibleBox = new JCheckBox("Disponible", disponible);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Titre :"));
        panel.add(titreField);
        panel.add(new JLabel("Auteur :"));
        panel.add(auteurField);
        panel.add(new JLabel("Année de publication :"));
        panel.add(anneeField);
        panel.add(new JLabel("Genre :"));
        panel.add(genreField);
        panel.add(disponibleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modifier l'ouvrage", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Ouvrage ouvrage = ouvrageRepository.findById(id);
                ouvrage.setTitre(titreField.getText());
                ouvrage.setAuteur(auteurField.getText());
                ouvrage.setAnneePublication(Integer.parseInt(anneeField.getText()));
                ouvrage.setGenre(genreField.getText());
                ouvrage.setDisponible(disponibleBox.isSelected());
                ouvrageRepository.update(ouvrage);
                loadOuvrages();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la modification : " + ex.getMessage(), "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Adaptation de supprimerOuvrage pour recevoir l'index
    private void supprimerOuvrage(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ouvrage à supprimer.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cet ouvrage ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object idObj = tableModel.getValueAt(selectedRow, 0);
            if (idObj != null) {
                ouvrageRepository.delete(ouvrageRepository.findById(idObj.toString()));
                loadOuvrages();
            }
        }
    }

}