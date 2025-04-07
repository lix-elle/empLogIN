import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeListWindow {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JButton addButton;

    public EmployeeListWindow() {
        frame = new JFrame("Employee List");
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("C:/Users/Lynsy/OneDrive/Desktop/Programming/Java programs/Employee LogIn/bluegame.png");
        frame.setIconImage(icon.getImage());

        // Use the correct image path
        String imagePath = "C:/Users/Lynsy/OneDrive/Desktop/Programming/Java programs/Employee LogIn/Sky Gradient.jpg";
        BackgroundPanel panel = new BackgroundPanel(imagePath);
        panel.setLayout(new BorderLayout());
        frame.setContentPane(panel);

        // Table setup
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Role", "Salary", ""}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only delete button column is editable
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Verdana", Font.PLAIN, 13));
        table.setRowHeight(30);

        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add Employee Button
        addButton = new JButton("Add Employee");
        addButton.setFont(new Font("Verdana", Font.BOLD, 14));
        addButton.setBackground(new Color(16, 52, 166));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(180, 40));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false); // Make top panel transparent for background visibility
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        topPanel.add(addButton);
        panel.add(topPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> showAddEmployeeDialog());

        frame.setVisible(true);
    }

    private void showAddEmployeeDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField salaryField = new JTextField();
        JComboBox<String> positionBox = new JComboBox<>(new String[]{"Project Manager", "Developer", "Analyst", "Designer", "Tester"});

        idField.setFont(new Font("Verdana", Font.PLAIN, 13));
        nameField.setFont(new Font("Verdana", Font.PLAIN, 13));
        salaryField.setFont(new Font("Verdana", Font.PLAIN, 13));
        positionBox.setFont(new Font("Verdana", Font.PLAIN, 13));

        ((AbstractDocument) idField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((AbstractDocument) salaryField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new AlphaDocumentFilter());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Number:") {{ setFont(new Font("Verdana", Font.BOLD, 13)); }}, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Name:") {{ setFont(new Font("Verdana", Font.BOLD, 13)); }}, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Role:") {{ setFont(new Font("Verdana", Font.BOLD, 13)); }}, gbc);
        gbc.gridx = 1;
        panel.add(positionBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Salary:") {{ setFont(new Font("Verdana", Font.BOLD, 13)); }}, gbc);
        gbc.gridx = 1;
        panel.add(salaryField, gbc);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Verdana", Font.BOLD, 13));
        okButton.setBackground(new Color(16, 52, 166));
        okButton.setForeground(Color.WHITE);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false); // Make bottom panel transparent for background visibility
        bottomPanel.add(okButton);

        // Use the BackgroundPanel in the dialog
        String imagePath = "C:/Users/Lynsy/OneDrive/Desktop/Programming/Java programs/Employee LogIn/Sky Gradient.jpg";
        BackgroundPanel dialogPanel = new BackgroundPanel(imagePath);
        dialogPanel.setLayout(new BorderLayout());
        dialogPanel.add(panel, BorderLayout.CENTER);
        dialogPanel.add(bottomPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(frame, "Add Employee Info", true);
        dialog.setContentPane(dialogPanel);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);

        okButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String role = (String) positionBox.getSelectedItem();
            String salary = salaryField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || salary.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill out all fields.");
                return;
            }

            model.addRow(new Object[]{id, name, role, salary, "X"});
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            } else {
                showError("Only numbers are allowed!");
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d+")) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                showError("Only numbers are allowed!");
            }
        }

        private void showError(String msg) {
            JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    class AlphaDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("[a-zA-Z\\s]+")) {
                super.insertString(fb, offset, string, attr);
            } else {
                showError("Only letters are allowed!");
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("[a-zA-Z\\s]+")) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                showError("Only letters are allowed!");
            }
        }

        private void showError(String msg) {
            JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("X");
            setFont(new Font("Verdana", Font.BOLD, 12));
            setForeground(Color.WHITE);
            setBackground(new Color(200, 80, 80));
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("X");
            button.setFont(new Font("Verdana", Font.BOLD, 12));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(200, 80, 80));
            button.setFocusPainted(false);
            button.addActionListener(e -> model.removeRow(selectedRow));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            selectedRow = row;
            return button;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeListWindow::new);
    }
}

// BackgroundPanel class - uses imagePath correctly
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            backgroundImage = icon.getImage();
        } else {
            JOptionPane.showMessageDialog(this, "Could not load background image.", "Image Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
