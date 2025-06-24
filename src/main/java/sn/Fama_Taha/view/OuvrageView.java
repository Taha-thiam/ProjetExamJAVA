package sn.Fama_Taha.view;
import sn.Fama_Taha.entity.Ouvrage;
import sn.Fama_Taha.repository.OuvrageRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;





public class OuvrageView extends JPanel {
    private JTable ouvrageTable;
    private DefaultTableModel tableModel;
    private OuvrageRepository ouvrageRepository = new OuvrageRepository();

    public OuvrageView() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Titre", "Auteur", "Année", "Genre", "Disponible"};
        tableModel = new DefaultTableModel(columns, 0);
        ouvrageTable = new JTable(tableModel);

        loadOuvrages();

        JScrollPane scrollPane = new JScrollPane(ouvrageTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter un ouvrage");
        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        addButton.addActionListener(_ -> ajouterOuvrage());
        editButton.addActionListener(_ -> modifierOuvrage());
        deleteButton.addActionListener(_ -> supprimerOuvrage());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
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
