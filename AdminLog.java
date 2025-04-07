import javax.swing.*;
import java.awt.*;

public class AdminLog {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLog() {
        frame = new JFrame("BSCS INTERNETAN LOGIN");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("C:/Users/Lynsy/OneDrive/Desktop/Programming/Java programs/Employee LogIn/bluegame.png");
        frame.setIconImage(icon.getImage());         
        
        BackgroundPanel panel = new BackgroundPanel("C:/Users/Lynsy/OneDrive/Desktop/Programming/Java programs/Employee LogIn/Sky Gradient.jpg");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;;

        // ===== Title Label =====
        JLabel company = new JLabel("BSCS INTERNETAN", JLabel.CENTER);
        company.setFont(new Font("Cooper Black", Font.PLAIN, 30));
        company.setForeground(new Color(16, 52, 166));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 30, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(company, gbc);

        // ===== Username Label =====
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Verdana", Font.PLAIN, 17));
        userLabel.setForeground(new Color(16, 52, 166));
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 25, 5, 0);
        panel.add(userLabel, gbc);

        // ===== Username Field =====
        usernameField = new RoundedTextField(20);
        usernameField.setFont(new Font("Verdana", Font.PLAIN, 14));
        usernameField.setForeground(new Color(16, 52, 166));
        usernameField.setPreferredSize(new Dimension(200, 35));
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        // ===== Password Label =====
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Verdana", Font.PLAIN, 17));
        passwordLabel.setForeground(new Color(16, 52, 166));
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        // ===== Password Field =====
        passwordField = new RoundedPasswordField(20);
        passwordField.setFont(new Font("Verdana", Font.PLAIN, 14));
        passwordField.setForeground(new Color(16, 52, 166));
        passwordField.setPreferredSize(new Dimension(200, 35));
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        // ===== Login Button =====
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Verdana", Font.BOLD, 14));
        loginButton.setBackground(new Color(16, 52, 166));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(145, 35));
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        // ===== Action =====
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            UIManager.put("OptionPane.messageFont", new Font("OLD BAKERSVILLE", Font.BOLD, 15));

            if (username.equals("Lixy") && password.equals("admin001")) {
                frame.dispose();
                new EmployeeListWindow();
            } else {
                UIManager.put("OptionPane.background", new Color(255, 194, 194));
                UIManager.put("Panel.background", new Color(255, 194, 194));
                UIManager.put("OptionPane.messageForeground", new Color(178, 34, 34));
                UIManager.put("Button.background", new Color(178, 34, 34));
                UIManager.put("Button.foreground", Color.WHITE);

                JOptionPane.showMessageDialog(frame, "Invalid credentials");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminLog::new);
    }
}

// Custom JPanel to Paint Background Image
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon("C:/Users/Lynsy/OneDrive/Desktop/Programming/Java programs/Employee LogIn/Sky Gradient.jpg").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
