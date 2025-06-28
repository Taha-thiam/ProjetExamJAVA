package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Amende;
import sn.Fama_Taha.repository.AmendeRepository;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AmendeView extends JPanel {
    private JTable amendeTable;
    private DefaultTableModel tableModel;
    private AmendeRepository amendeRepository = new AmendeRepository();

    public AmendeView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Colonnes avec état et bouton changer état
        String[] columns = {"ID", "Montant", "Date", "Raison", "Membre", "État", "Changer état", "Supprimer"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                // Seules les colonnes "Changer état" et "Supprimer" sont éditables (boutons)
                return column == 6 || column == 7;
            }
        };
        amendeTable = new JTable(tableModel);
        amendeTable.setRowHeight(28);

        // Ajout des renderers et editors pour les boutons
        amendeTable.getColumn("Changer état").setCellRenderer(new ButtonRenderer("Changer état", new Color(52, 152, 219)));
        amendeTable.getColumn("Changer état").setCellEditor(new ButtonEditor(new JCheckBox(), "Changer état"));
        amendeTable.getColumn("Supprimer").setCellRenderer(new ButtonRenderer("Supprimer", new Color(231, 76, 60)));
        amendeTable.getColumn("Supprimer").setCellEditor(new ButtonEditor(new JCheckBox(), "Supprimer"));

        loadAmendes();

        JScrollPane scrollPane = new JScrollPane(amendeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);

        JLabel title = new JLabel("Liste des Amendes");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);
    }

    private void loadAmendes() {
        tableModel.setRowCount(0);
        List<Amende> amendes = amendeRepository.findAll();
        for (Amende a : amendes) {
            tableModel.addRow(new Object[]{
                a.getIdAmende(),
                a.getMontant(),
                a.getDateAmende(),
                a.getRaison(),
                a.getMembre() != null ? a.getMembre().getNom() + " " + a.getMembre().getPrenom() : "",
                a.isAmendePayer() ? "Payée" : "Impayée",
                "Changer état",
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
            if ("Supprimer".equals(actionType)) {
                button.setBackground(new Color(231, 76, 60));
            } else {
                button.setBackground(new Color(52, 152, 219));
            }
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    selectedRow = amendeTable.getSelectedRow();
                    Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
                    if ("Supprimer".equals(actionType)) {
                        int confirm = JOptionPane.showConfirmDialog(button, "Supprimer cette amende ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            Amende amende = amendeRepository.findById(id);
                            if (amende != null) {
                                amendeRepository.delete(amende);
                                loadAmendes();
                            }
                        }
                    } else if ("Changer état".equals(actionType)) {
                        Amende amende = amendeRepository.findById(id);
                        if (amende != null) {
                            amende.setAmendePayer(!amende.isAmendePayer());
                            amendeRepository.update(amende);
                            loadAmendes();
                        }
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
}