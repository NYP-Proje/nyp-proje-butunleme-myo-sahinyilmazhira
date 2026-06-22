package Ntp;

import java.sql.*;
import java.util.ArrayList;

public class Veritabanı {
    private String url = "jdbc:sqlite:gider_takip.db";

    public Veritabanı() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                
                String sql = "CREATE TABLE IF NOT EXISTS giderler ("
                           + "  id INTEGER PRIMARY KEY AUTOINCREMENT,"
                           + "  miktar REAL NOT NULL,"
                           + "  kategori TEXT NOT NULL,"
                           + "  tarih TEXT NOT NULL,"
                           + "  aciklama TEXT"
                           + ");";
                stmt.execute(sql);
            }
        } catch (Exception e) {
            System.out.println("Gider tablosu oluşturulurken hata oluştu: " + e.getMessage());
        }
    }

    public void giderEkle(double miktar, String kategori, String tarih, String aciklama) {
        String sql = "INSERT INTO giderler(miktar, kategori, tarih, aciklama) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, miktar);
            pstmt.setString(2, kategori);
            pstmt.setString(3, tarih);
            pstmt.setString(4, aciklama);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Gider eklenirken hata oluştu: " + e.getMessage());
        }
    }

    public boolean giderSil(int id) {
        String sql = "DELETE FROM giderler WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("Gider silinirken hata oluştu: " + e.getMessage());
        }
        return false;
    }

    public boolean giderGuncelle(int id, double yeniMiktar, String yeniKategori, String yeniTarih, String yeniAciklama) {
        String sql = "UPDATE giderler SET miktar = ?, kategori = ?, tarih = ?, aciklama = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, yeniMiktar);
            pstmt.setString(2, yeniKategori);
            pstmt.setString(3, yeniTarih);
            pstmt.setString(4, yeniAciklama);
            pstmt.setInt(5, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            System.out.println("Gider güncellenirken hata oluştu: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Gider> getGiderler() {
        ArrayList<Gider> giderListesi = new ArrayList<>();
        String sql = "SELECT id, miktar, kategori, tarih, aciklama FROM giderler ORDER BY id ASC";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Gider g = new Gider(
                    rs.getInt("id"),
                    rs.getDouble("miktar"),
                    rs.getString("kategori"),
                    rs.getString("tarih"),
                    rs.getString("aciklama")
                );
                giderListesi.add(g);
            }
        } catch (Exception e) {
            System.out.println("Giderler yüklenirken hata oluştu: " + e.getMessage());
        }
        return giderListesi;
    }

    public double kategoriyeGoreToplam(String kategori) {
        String sql = "SELECT SUM(miktar) AS toplam FROM giderler WHERE LOWER(kategori) = LOWER(?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, kategori);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("toplam");
                }
            }
        } catch (Exception e) {
            System.out.println("Kategori bazlı rapor alınırken hata oluştu: " + e.getMessage());
        }
        return 0.0;
    }
}