package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Emprunt;
import sn.Fama_Taha.repository.EmpruntRepository;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;




public class EmpruntView extends JPanel {
    private JTable empruntTable;
    private DefaultTableModel tableModel;
    private EmpruntRepository empruntRepository = new EmpruntRepository();

    public EmpruntView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        String[] columns = {"ID", "Date Emprunt", "Retour Prévue", "Retour Réelle", "Membre", "Ouvrage"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
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
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        empruntTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
            tableModel.addRow(new Object[]{
                e.getIdEmprunt(),
                e.getDateEmprunt() != null ? e.getDateEmprunt().format(fmt) : "",
                e.getDateRetourPrevue() != null ? e.getDateRetourPrevue().format(fmt) : "",
                e.getDateRetourReelle() != null ? e.getDateRetourReelle().format(fmt) : "",
                e.getMembre() != null ? e.getMembre().getNom() + " " + e.getMembre().getPrenom() : "",
                e.getOuvrage() != null ? e.getOuvrage().getTitre() : ""
            });
        }
    }

    private void ajouterEmprunt() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité d'ajout à implémenter.");
    }

    private void modifierEmprunt() {
        int selectedRow = empruntTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt à modifier.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Fonctionnalité de modification à implémenter.");
    }

    private void supprimerEmprunt() {
        int selectedRow = empruntTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt à supprimer.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cet emprunt ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Object idObj = tableModel.getValueAt(selectedRow, 0);
            if (idObj != null) {
                empruntRepository.delete(empruntRepository.findById(idObj.toString()));
                loadEmprunts();
            }
        }
    }
}
