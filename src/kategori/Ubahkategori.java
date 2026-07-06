package kategori;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import koneksi.koneksi;

public class Ubahkategori extends JDialog {

    private JTextField txtNama;
    private JButton btnUpdate, btnBatal;
    private final Datakategori parent;
    private final int idKategori;

    /**
     *
     * @param parent
     * @param idKategori
     * @param namaLama
     */
    public Ubahkategori(Datakategori parent, int idKategori, String namaLama) {
        super(parent, "Ubah Kategori", true);
        this.parent = parent;
        this.idKategori = idKategori;
        setSize(350, 180);
        setLocationRelativeTo(parent);
        initComponents();
        txtNama.setText(namaLama);
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
        btnUpdate = new JButton("Update");
        btnBatal = new JButton("Batal");
        panelBtn.add(btnUpdate);
        panelBtn.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(panelBtn, gbc);

        add(panel);

        btnUpdate.addActionListener(e -> updateData());
        btnBatal.addActionListener(e -> dispose());
    }

    private void updateData() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori wajib diisi!");
            return;
        }

        String sql = "UPDATE kategori SET nama_kategori=? WHERE id_kategori=?";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setInt(2, idKategori);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
            parent.tampilkanData("");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + e.getMessage());
        }
    }
}
