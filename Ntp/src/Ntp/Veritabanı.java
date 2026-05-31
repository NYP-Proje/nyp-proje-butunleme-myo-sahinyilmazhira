package Ntp;

import java.io.*;
import java.util.ArrayList;

public class Veritabanı {
    private ArrayList<Gider> giderListesi = new ArrayList<>();
    private int sonId = 0;

    public Veritabanı() {
        verileriYukle();
    }

    public void giderEkle(double miktar, String kategori, String tarih, String aciklama) {
        sonId = sonId + 1;
        Gider yeniGider = new Gider(sonId, miktar, kategori, tarih, aciklama);
        giderListesi.add(yeniGider);
        verileriKaydet();
    }


    public boolean giderSil(int id) {
        for (int i = 0; i < giderListesi.size(); i++) {
            Gider g = giderListesi.get(i);
            if (g.getId() == id) {
                giderListesi.remove(i);
                verileriKaydet();
                return true;
            }
        }
        return false;
    }


    public boolean giderGuncelle(int id, double yeniMiktar, String yeniKategori, String yeniTarih, String yeniAciklama) {
        for (int i = 0; i < giderListesi.size(); i++) {
            Gider g = giderListesi.get(i);
            if (g.getId() == id) {
                g.setMiktar(yeniMiktar);
                g.setKategori(yeniKategori);
                g.setTarih(yeniTarih);
                g.setAciklama(yeniAciklama);
                verileriKaydet();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Gider> getGiderler() {
        return giderListesi;
    }

    public double kategoriyeGoreToplam(String kategori) {
        double toplam = 0;
        for (int i = 0; i < giderListesi.size(); i++) {
            Gider g = giderListesi.get(i);
            if (g.getKategori().equalsIgnoreCase(kategori)) {
                toplam = toplam + g.getMiktar();
            }
        }
        return toplam;
    }

    private void verileriKaydet() {
        try {
            PrintWriter yazici = new PrintWriter(new FileWriter("giderler.txt"));
            for (int i = 0; i < giderListesi.size(); i++) {
                Gider g = giderListesi.get(i);
                yazici.println(g.getId() + ";" + g.getMiktar() + ";" + g.getKategori() + ";" + g.getTarih() + ";" + g.getAciklama());
            }
            yazici.close();
        } catch (Exception e) {
            System.out.println("Dosya yazma hatası!");
        }
    }

    private void verileriYukle() {
        try {
            File dosya = new File("giderler.txt");
            if (!dosya.exists()) {
                return;
            }

            BufferedReader okuyucu = new BufferedReader(new FileReader(dosya));
            String satir;
            while ((satir = okuyucu.readLine()) != null) {
                String[] parcalar = satir.split(";");
                if (parcalar.length == 5) {
                    int id = Integer.parseInt(parcalar[0]);
                    double miktar = Double.parseDouble(parcalar[1]);
                    String kategori = parcalar[2];
                    String tarih = parcalar[3];
                    String aciklama = parcalar[4];

                    giderListesi.add(new Gider(id, miktar, kategori, tarih, aciklama));
                    if (id > sonId) {
                        sonId = id;
                    }
                }
            }
            okuyucu.close();
        } catch (Exception e) {
            System.out.println("Dosya okuma hatası!");
        }
    }
}