package Ntp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GuvenlikServisi {
    private String url = "jdbc:sqlite:gider_takip.db";

    public GuvenlikServisi() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                
                String sql = "CREATE TABLE IF NOT EXISTS kullanicilar ("
                           + "  kullanici_adi TEXT PRIMARY KEY,"
                           + "  sifre TEXT NOT NULL"
                           + ");";
                stmt.execute(sql);
            }
        } catch (Exception e) {
            System.out.println("Veritabanı başlatılırken hata oluştu: " + e.getMessage());
        }
    }

    public boolean kayitliKullaniciVarMi() {
        String sql = "SELECT COUNT(*) AS total FROM kullanicilar";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (Exception e) {
            System.out.println("Kullanıcı kontrolü yapılırken hata oluştu: " + e.getMessage());
        }
        return false;
    }

    public void kayitOl(String kAdi, String sifre) {
        String sql = "INSERT OR REPLACE INTO kullanicilar(kullanici_adi, sifre) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, kAdi);
            pstmt.setString(2, sifre);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Kayıt esnasında hata oluştu: " + e.getMessage());
        }
    }

    public boolean girisYap(String kAdi, String sifre) {
        String sql = "SELECT COUNT(*) AS total FROM kullanicilar WHERE kullanici_adi = ? AND sifre = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, kAdi);
            pstmt.setString(2, sifre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Giriş kontrolü yapılırken hata oluştu: " + e.getMessage());
        }
        return false;
    }
}