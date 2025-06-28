package sn.Fama_Taha.view;

import sn.Fama_Taha.entity.Reservation;
import sn.Fama_Taha.repository.ReservationRepository;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReservationView extends JPanel {
    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private ReservationRepository reservationRepository = new ReservationRepository();

    public ReservationView() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Colonnes du tableau
        String[] columns = { "ID", "Date", "Membre", "Ouvrage", "État", "Annuler" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Seule la colonne "Annuler" est éditable (bouton)
            }
        };
        reservationTable = new JTable(tableModel);
        reservationTable.setRowHeight(28);

        // Design du tableau
        reservationTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        reservationTable.setSelectionBackground(new Color(174, 214, 241));
        reservationTable.setSelectionForeground(Color.BLACK);
        reservationTable.setShowGrid(false);

        JTableHeader header = reservationTable.getTableHeader();
        header.setBackground(new Color(41, 128, 185));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        reservationTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(236, 240, 241) : Color.WHITE);
                } else {
                    c.setBackground(new Color(174, 214, 241));
                }
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        // Renderer et editor pour le bouton "Annuler"
        reservationTable.getColumn("Annuler").setCellRenderer(new ButtonRenderer("Annuler", new Color(231, 76, 60)));
        reservationTable.getColumn("Annuler").setCellEditor(new ButtonEditor(new JCheckBox(), "Annuler"));

        loadReservations();

        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter une réservation");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        addButton.addActionListener(_ -> ajouterReservation());

        JLabel title = new JLabel("Gestion des Réservations");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(245, 245, 250));
        topPanel.add(addButton);
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);
    }

    private void loadReservations() {
        tableModel.setRowCount(0);
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation r : reservations) {
            tableModel.addRow(new Object[] {
                    r.getIdReservation(),
                    r.getDateReservation(),
                    r.getMembre() != null ? r.getMembre().getNom() + " " + r.getMembre().getPrenom() : "",
                    r.getOuvrage() != null ? r.getOuvrage().getTitre() : "",
                    r.isActive() ? "Active" : "Annulée",
                    "Annuler"
            });
        }
    }

    private void ajouterReservation() {
        JTextField idField = new JTextField();
        JTextField dateField = new JTextField();

        // Récupère les membres et ouvrages disponibles
        List<sn.Fama_Taha.entity.Membre> membres = new sn.Fama_Taha.repository.MembreRepository().findAll();
        JComboBox<sn.Fama_Taha.entity.Membre> membreBox = new JComboBox<>(
                membres.toArray(new sn.Fama_Taha.entity.Membre[0]));
        membreBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof sn.Fama_Taha.entity.Membre) {
                    sn.Fama_Taha.entity.Membre m = (sn.Fama_Taha.entity.Membre) value;
                    setText(m.getNom() + " " + m.getPrenom());
                }
                return this;
            }
        });

        List<sn.Fama_Taha.entity.Ouvrage> ouvrages = new sn.Fama_Taha.repository.OuvrageRepository().findAll();
        JComboBox<sn.Fama_Taha.entity.Ouvrage> ouvrageBox = new JComboBox<>(
                ouvrages.toArray(new sn.Fama_Taha.entity.Ouvrage[0]));
        ouvrageBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof sn.Fama_Taha.entity.Ouvrage) {
                    sn.Fama_Taha.entity.Ouvrage o = (sn.Fama_Taha.entity.Ouvrage) value;
                    setText(o.getTitre());
                }
                return this;
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("ID réservation :"));
        panel.add(idField);
        panel.add(new JLabel("Date réservation (yyyy-MM-dd) :"));
        panel.add(dateField);
        panel.add(new JLabel("Membre :"));
        panel.add(membreBox);
        panel.add(new JLabel("Ouvrage :"));
        panel.add(ouvrageBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter une réservation", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Reservation reservation = new Reservation();
                reservation.setIdReservation(idField.getText());
                reservation.setDateReservation(java.time.LocalDate.parse(dateField.getText()));
                reservation.setMembre((sn.Fama_Taha.entity.Membre) membreBox.getSelectedItem());
                reservation.setOuvrage((sn.Fama_Taha.entity.Ouvrage) ouvrageBox.getSelectedItem());
                reservation.setActive(true);
                reservationRepository.save(reservation);
                loadReservations();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ButtonRenderer et ButtonEditor à reprendre de tes autres vues
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

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String actionType;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
            button = new JButton(actionType);
            button.setOpaque(true);
            button.setBackground(new Color(231, 76, 60));
            button.setForeground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    selectedRow = reservationTable.getSelectedRow();
                    String id = (String) tableModel.getValueAt(selectedRow, 0);
                    if ("Annuler".equals(actionType)) {
                        int confirm = JOptionPane.showConfirmDialog(button, "Annuler cette réservation ?",
                                "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            Reservation reservation = reservationRepository.findById(id);
                            if (reservation != null) {
                                reservation.setActive(false);
                                reservationRepository.update(reservation);
                                loadReservations();
                            }
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