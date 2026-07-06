package menu;

import koneksi.koneksi;

import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class Tambahmenu extends JDialog {

    private JTextField txtNama, txtHarga, txtStok;
    private JComboBox<String> cbKategori;
    private JButton btnSimpan, btnBatal;
    private final Datamenu parent;
    private final Map<String, Integer> mapKategori = new LinkedHashMap<>(); // nama -> id

    public Tambahmenu(Datamenu parent) {
        super(parent, "Tambah Menu", true);
        this.parent = parent;
        setSize(380, 280);
        setLocationRelativeTo(parent);
        initComponents();
        muatKategori();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nama Menu :"), gbc);
        txtNama = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Harga :"), gbc);
        txtHarga = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtHarga, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Stok :"), gbc);
        txtStok = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtStok, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Kategori :"), gbc);
        cbKategori = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(cbKategori, gbc);

        JPanel panelBtn = new JPanel(new FlowLayout());
        btnSimpan = new JButton("Simpan");
        btnBatal = new JButton("Batal");
        panelBtn.add(btnSimpan);
        panelBtn.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(panelBtn, gbc);

        add(panel);

        btnSimpan.addActionListener(e -> simpanData());
        btnBatal.addActionListener(e -> dispose());
    }

    private void muatKategori() {
        String sql = "SELECT id_kategori, nama_kategori FROM kategori ORDER BY nama_kategori";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nama = rs.getString("nama_kategori");
                mapKategori.put(nama, rs.getInt("id_kategori"));
                cbKategori.addItem(nama);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat kategori: " + e.getMessage());
        }
    }

    private void simpanData() {
        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stokStr = txtStok.getText().trim();
        String kategoriTerpilih = (String) cbKategori.getSelectedItem();

        if (nama.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty() || kategoriTerpilih == null) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }

        double harga;
        int stok;
        try {
            harga = Double.parseDouble(hargaStr);
            stok = Integer.parseInt(stokStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka!");
            return;
        }

        int idKategori = mapKategori.get(kategoriTerpilih);

        String sql = "INSERT INTO menu(nama_menu, harga, stok, id_kategori) VALUES (?,?,?,?)";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setDouble(2, harga);
            ps.setInt(3, stok);
            ps.setInt(4, idKategori);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            parent.tampilkanData("");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        }
    }
}
