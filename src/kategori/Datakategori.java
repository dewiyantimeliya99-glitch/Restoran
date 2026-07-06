package kategori;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

public final class Datakategori extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtCari;
    private JButton btnCari, btnTambah, btnUbah, btnHapus, btnRefresh;

    public Datakategori() {
        setTitle("Data Kategori");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 420);
        setLocationRelativeTo(null);
        initComponents();
        tampilkanData("");
    }

    private void initComponents() {
        JPanel panelAtas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAtas.add(new JLabel("Cari :"));
        txtCari = new JTextField(20);
        btnCari = new JButton("Cari");
        panelAtas.add(txtCari);
        panelAtas.add(btnCari);
        add(panelAtas, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID", "Nama Kategori"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new FlowLayout());
        
        btnTambah = new JButton("Tambah");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");
        
        panelBawah.add(btnTambah);
        panelBawah.add(btnUbah);
        panelBawah.add(btnHapus);
        panelBawah.add(btnRefresh);
        add(panelBawah, BorderLayout.SOUTH);

        btnCari.addActionListener(e -> tampilkanData(txtCari.getText().trim()));
        
        btnRefresh.addActionListener(e -> {
            txtCari.setText("");
            tampilkanData("");
        });
        
        btnTambah.addActionListener(e -> {
            Tambahkategori dialog = new Tambahkategori(this);
            dialog.setVisible(true);
        });
        
        btnUbah.addActionListener(e -> ubahData());
        
        btnHapus.addActionListener(e -> hapusData());
    }

    public void tampilkanData(String keyword) {
        model.setRowCount(0);
        String sql = "SELECT * FROM kategori WHERE nama_kategori LIKE ? ORDER BY id_kategori";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_kategori"),
                        rs.getString("nama_kategori")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
        }
    }

    private void ubahData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah terlebih dahulu!");
            return;
        }
        int id = (int) model.getValueAt(row, 0); 
        String namaLama = model.getValueAt(row, 1).toString(); 
        new Ubahkategori(this, id, namaLama).setVisible(true);
    }

    private void hapusData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu!");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM kategori WHERE id_kategori=?";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            tampilkanData("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data (mungkin masih dipakai di tabel Menu): "
                    + e.getMessage());
        }
    }
}
