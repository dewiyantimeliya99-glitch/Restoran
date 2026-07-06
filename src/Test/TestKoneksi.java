package Test;

import koneksi.koneksi;
import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {

        if (koneksi.getConnection() != null) {
            System.out.println("BERHASIL");
        } else {
            System.out.println("GAGAL");
        }

    }

}