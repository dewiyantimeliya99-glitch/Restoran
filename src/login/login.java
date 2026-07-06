package login;

import koneksi.koneksi;
import menuutama.MenuUtama;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class login extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnKeluar;

    public login() {
        setTitle("Login - Sistem Informasi Restoran");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("LOGIN APLIKASI", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username :"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password :"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("Login");
        btnKeluar = new JButton("Keluar");
        JPanel panelBtn = new JPanel();
        panelBtn.add(btnLogin);
        panelBtn.add(btnKeluar);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(panelBtn, gbc);

        add(panel);

        btnLogin.addActionListener(e -> prosesLogin());
        btnKeluar.addActionListener(e -> System.exit(0));
    }

    private void prosesLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi username dan password!");
            return;
        }

        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = koneksi.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi database gagal!");
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String nama = rs.getString("nama_lengkap");
                        JOptionPane.showMessageDialog(this, "Login berhasil");
                        new MenuUtama(nama).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Login gagal");
                        txtPassword.setText("");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage());
        }
    }
}