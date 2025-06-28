package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Membre;
import sn.Fama_Taha.repository.MembreRepository;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
// import java.awt.event.*;
import java.util.List;

public class MembreView extends JPanel {
    private JTable membreTable;
    private DefaultTableModel tableModel;
    private MembreRepository membreRepository = new MembreRepository();

    public MembreView() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Titre stylisé
        JLabel title = new JLabel("Gestion des Membres");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(44, 62, 80));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Bouton ajouter stylisé
        JButton addButton = new JButton("Ajouter un membre");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addButton.setFocusPainted(false);
        addButton.setBorder(new RoundedBorder(18));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setPreferredSize(new Dimension(200, 40));
        addButton.addActionListener(_ -> ajouterMembre());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        topPanel.setOpaque(false);
        topPanel.add(addButton);

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setOpaque(false);
        topWrapper.add(topPanel, BorderLayout.CENTER);
        topWrapper.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
        add(topWrapper, BorderLayout.BEFORE_FIRST_LINE);

        // Colonnes du tableau
        String[] columns = { "ID", "Nom", "Prénom", "Email", "Téléphone", "Type", "Modifier", "Supprimer" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7;
            }
        };
        membreTable = new JTable(tableModel);

        // Style du tableau
        membreTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        membreTable.setRowHeight(32);
        membreTable.setSelectionBackground(new Color(102, 178, 255, 120));
        membreTable.setSelectionForeground(Color.BLACK);
        membreTable.setShowHorizontalLines(false);
        membreTable.setShowVerticalLines(false);

        JTableHeader header = membreTable.getTableHeader();
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        membreTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 248, 255) : Color.WHITE);
                } else {
                    c.setBackground(new Color(102, 178, 255, 120));
                }
                setHorizontalAlignment(CENTER);
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

        membreTable.getColumn("Modifier").setCellRenderer(new ButtonRenderer("Modifier", new Color(241, 196, 15)));
        membreTable.getColumn("Modifier").setCellEditor(new ButtonEditor(new JCheckBox(), "Modifier"));
        membreTable.getColumn("Supprimer").setCellRenderer(new ButtonRenderer("Supprimer", new Color(231, 76, 60)));
        membreTable.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), "Supprimer"));

        loadMembres();

        // Carte arrondie pour le tableau
        JPanel cardPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(236, 240, 251), 0, getHeight(), new Color(200, 220, 255));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        JScrollPane scrollPane = new JScrollPane(membreTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        cardPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(cardPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 40, 60));
        add(centerPanel, BorderLayout.CENTER);
    }

    // Ajout d'un fond dégradé
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, new Color(236, 240, 251), 0, getHeight(), new Color(200, 220, 255));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void ajouterMembre() {
        JTextField idField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telephoneField = new JTextField();
        JTextField typeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 8));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(44, 62, 80, 60), 1, true),
                new EmptyBorder(15, 20, 15, 20)
        ));
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

    private void loadMembres() {
        tableModel.setRowCount(0);
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

    // Renderer pour les boutons stylisés
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text, Color color) {
            setText(text);
            setOpaque(true);
            setBackground(color);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setBorder(new RoundedBorder(12));
            setFocusPainted(false);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground("Modifier".equals(getText()) ? new Color(241, 196, 15) : new Color(231, 76, 60));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            return this;
        }
    }

    // Editor pour les boutons stylisés
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String actionType;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
            button = new JButton(actionType);
            button.setOpaque(true);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setBorder(new RoundedBorder(12));
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            button.setBackground("Modifier".equals(actionType) ? new Color(241, 196, 15) : new Color(231, 76, 60));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addActionListener(_ -> {
                fireEditingStopped();
                selectedRow = membreTable.getSelectedRow();
                if ("Modifier".equals(actionType)) {
                    modifierMembre(selectedRow);
                } else if ("Supprimer".equals(actionType)) {
                    supprimerMembre(selectedRow);
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

        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 8));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(44, 62, 80, 60), 1, true),
                new EmptyBorder(15, 20, 15, 20)
        ));
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

    // Bordure arrondie personnalisée pour les boutons
    static class RoundedBorder extends AbstractBorder {
        private int radius;
        public RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0,0,0,30));
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(6, 18, 6, 18);
        }
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 18;
            insets.top = insets.bottom = 6;
            return insets;
        }
    }
}