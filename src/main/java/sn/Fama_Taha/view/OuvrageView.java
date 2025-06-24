package sn.Fama_Taha.view;
import sn.Fama_Taha.entity.Ouvrage;
import sn.Fama_Taha.repository.OuvrageRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OuvrageView extends JPanel {
    private JTable ouvrageTable;
    private DefaultTableModel tableModel;
    private OuvrageRepository ouvrageRepository = new OuvrageRepository();

    public OuvrageView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        String[] columns = {"ID", "Titre", "Auteur", "Année", "Genre", "Disponible"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ouvrageTable = new JTable(tableModel);
        ouvrageTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        ouvrageTable.setRowHeight(28);
        ouvrageTable.setGridColor(new Color(220, 220, 220));
        ouvrageTable.setSelectionBackground(new Color(102, 178, 255));
        ouvrageTable.setSelectionForeground(Color.BLACK);

        // En-tête du tableau stylisée
        ouvrageTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        ouvrageTable.getTableHeader().setBackground(new Color(51, 102, 204));
        ouvrageTable.getTableHeader().setForeground(Color.WHITE);

        // Cellules alternées colorées
        ouvrageTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(235, 243, 255) : Color.WHITE);
                } else {
                    c.setBackground(new Color(102, 178, 255));
                }
                return c;
            }
        });

        loadOuvrages();

        JScrollPane scrollPane = new JScrollPane(ouvrageTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter un ouvrage");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        // Style des boutons
        Color btnColor = new Color(51, 102, 204);
        Font btnFont = new Font("Segoe UI", Font.BOLD, 15);
        for (JButton btn : new JButton[]{addButton, editButton, deleteButton}) {
            btn.setBackground(btnColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(btnFont);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        }

        addButton.addActionListener(_ -> ajouterOuvrage());
        editButton.addActionListener(_ -> modifierOuvrage());
        deleteButton.addActionListener(_ -> supprimerOuvrage());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Titre en haut
        JLabel titre = new JLabel("Gestion des Ouvrages");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titre.setForeground(new Color(51, 102, 204));
        titre.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, BorderLayout.NORTH);
    }

    private void loadOuvrages() {
        tableModel.setRowCount(0);
        List<Ouvrage> ouvrages = ouvrageRepository.findAll();
        for (Ouvrage o : ouvrages) {
            tableModel.addRow(new Object[]{
                o.getIdOuvrage(),
                o.getTitre(),
                o.getAuteur(),
                o.getAnneePublication(),
                o.getGenre(),
                o.isDisponible() ? "Oui" : "Non"
            });
        }
    }

    private void ajouterOuvrage() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité d'ajout à implémenter.");
    }

    private void modifierOuvrage() {
        int selectedRow = ouvrageTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ouvrage à modifier.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Fonctionnalité de modification à implémenter.");
    }

    private void supprimerOuvrage() {
        int selectedRow = ouvrageTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un ouvrage à supprimer.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cet ouvrage ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object idObj = tableModel.getValueAt(selectedRow, 0);
            if (idObj != null) {
                ouvrageRepository.delete(ouvrageRepository.findById(idObj.toString()));
                loadOuvrages();
            }
        }
    }
}
