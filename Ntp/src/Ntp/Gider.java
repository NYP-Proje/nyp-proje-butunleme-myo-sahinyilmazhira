package Ntp;

public class Gider {
    private int id;
    private double miktar;
    private String kategori;
    private String tarih;
    private String aciklama;

    public Gider(int id, double miktar, String kategori, String tarih, String aciklama) {
        this.id = id;
        this.miktar = miktar;
        this.kategori = kategori;
        this.tarih = tarih;
        this.aciklama = aciklama;
    }

    public int getId() {
        return id;
    }

    public double getMiktar() {
        return miktar;
    }

    public void setMiktar(double miktar) {
        this.miktar = miktar;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}