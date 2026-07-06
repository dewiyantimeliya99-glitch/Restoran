package menuutama;

import kategori.Datakategori;
import login.login;

import java.awt.*;
import javax.swing.*;
import menu.Datamenu;

public class MenuUtama extends JFrame {

    private final String namaUser;

    public MenuUtama(String namaUser) {
        this.namaUser = namaUser;
        setTitle("Menu Utama - Sistem Informasi Menu Restoran");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // ===== Menu Bar =====
        JMenuBar menuBar = new JMenuBar();

        JMenu menuMaster = new JMenu("Master");
        JMenuItem itemDataKategori = new JMenuItem("Data Kategori");
        JMenuItem itemDataMenu = new JMenuItem("Data Menu");
        menuMaster.add(itemDataKategori);
        menuMaster.add(itemDataMenu);

        JMenu menuAkun = new JMenu("Akun");
        JMenuItem itemLogout = new JMenuItem("Logout");
        menuAkun.add(itemLogout);

        JMenuItem itemKeluar = new JMenuItem("Keluar");

        menuBar.add(menuMaster);
        menuBar.add(menuAkun);
        menuBar.add(itemKeluar);
        setJMenuBar(menuBar);

        // ===== Panel tengah =====
        JPanel panelTengah = new JPanel(new BorderLayout());
        JLabel lblSelamat = new JLabel(
                "<html><div style='text-align:center;'>"
                + "<h1>Sistem Informasi Menu Restoran</h1>"
                + "<p>Selamat datang, " + namaUser + "</p>"
                + "<p>Silakan pilih menu <b>Master</b> untuk mengelola data Kategori atau Menu.</p>"
                + "</div></html>", SwingConstants.CENTER);
        lblSelamat.setHorizontalAlignment(SwingConstants.CENTER);
        panelTengah.add(lblSelamat, BorderLayout.CENTER);
        add(panelTengah);

        // ===== Actions =====
        itemDataKategori.addActionListener(e -> new Datakategori().setVisible(true));
        itemDataMenu.addActionListener(e -> new Datamenu().setVisible(true));
       

        itemLogout.addActionListener(e -> {
            int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (pilih == JOptionPane.YES_OPTION) {
                new login().setVisible(true);
                this.dispose();
            }
        });

        itemKeluar.addActionListener(e -> {
            int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar aplikasi?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (pilih == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}
