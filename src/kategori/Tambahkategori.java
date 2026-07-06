package kategori;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import koneksi.koneksi;

public class Tambahkategori extends JDialog {

    private JTextField txtNama;
    private JButton btnSimpan, btnBatal;
    private final Datakategori parent;

    public Tambahkategori(Datakategori parent) {
        super(parent, "Tambah Kategori", true);
        this.parent = parent;
        setSize(350, 180);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nama Kategori :"), gbc);
        txtNama = new JTextField(18);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtNama, gbc);

        JPanel panelBtn = new JPanel(new FlowLayout());
        btnSimpan = new JButton("Simpan");
        btnBatal = new JButton("Batal");
        panelBtn.add(btnSimpan);
        panelBtn.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(panelBtn, gbc);

        add(panel);

        btnSimpan.addActionListener(e -> simpanData());
        btnBatal.addActionListener(e -> dispose());
    }

    private void simpanData() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori wajib diisi!");
            return;
        }

        String sql = "INSERT INTO kategori(nama_kategori) VALUES (?)";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            parent.tampilkanData("");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        }
    }
}
