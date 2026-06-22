package Ntp;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Veritabanı db = new Veritabanı();
        GuvenlikServisi guvenlik = new GuvenlikServisi();

        if (guvenlik.kayitliKullaniciVarMi() == false) {
            System.out.println("*** KULLANICI KAYIT EKRANI ***");
            System.out.println("Sistemi ilk kez baslatmak icin bir hesap olusturun.");
            System.out.print("Kullanici Adi Belirleyin: ");
            String yeniKullanici = scanner.nextLine();
            System.out.print("Sifre Belirleyin: ");
            String yeniSifre = scanner.nextLine();

            guvenlik.kayitOl(yeniKullanici, yeniSifre);
            System.out.println("Hesap basariyla olusturuldu!\n");
        }

        System.out.println("*** KULLANICI GIRISI ***");
        System.out.print("Kullanici Adi: ");
        String kAdi = scanner.nextLine();
        System.out.print("Sifre: ");
        String sifre = scanner.nextLine();

        if (guvenlik.girisYap(kAdi, sifre) == false) {
            System.out.println("Hatali giris yaptiniz! Program kapatiliyor.");
            return;
        }

        System.out.println("Giris basarili! Hos geldiniz.");

        while (true) {
            System.out.println("\n--- GIDER TAKIP SISTEMI ---");
            System.out.println("1. Gider Ekle");
            System.out.println("2. Gider Sil");
            System.out.println("3. Gider Guncelle");
            System.out.println("4. Tum Giderleri Listele");
            System.out.println("5. Kategori Bazli Rapor Al");
            System.out.println("6. Cikis");
            System.out.print("Seciminiz (1-6): ");

            String secim = scanner.nextLine();

            try {
                if (secim.equals("1")) {
                    System.out.print("Miktar (TL): ");
                    double miktar = Double.parseDouble(scanner.nextLine());
                    System.out.print("Kategori: ");
                    String kategori = scanner.nextLine();
                    System.out.print("Tarih (GG.AA.YYYY): ");
                    String tarih = scanner.nextLine();
                    System.out.print("Aciklama: ");
                    String aciklama = scanner.nextLine();

                    db.giderEkle(miktar, kategori, tarih, aciklama);
                    System.out.println("Gider basariyla eklendi.");

                } else if (secim.equals("2")) {
                    System.out.print("Silinecek Giderin ID Numarasi: ");
                    int silinecekId = Integer.parseInt(scanner.nextLine());

                    if (db.giderSil(silinecekId) == true) {
                        System.out.println("Gider basariyla silindi.");
                    } else {
                        System.out.println("HATA: Bu ID bulunamadi!");
                    }

                } else if (secim.equals("3")) {
                    System.out.print("Guncellenecek Giderin ID Numarasi: ");
                    int guncelleId = Integer.parseInt(scanner.nextLine());

                    Gider bulunanGider = null;
                    ArrayList<Gider> list = db.getGiderler();

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId() == guncelleId) {
                            bulunanGider = list.get(i);
                            break;
                        }
                    }

                    if (bulunanGider == null) {
                        System.out.println("HATA: Bu ID bulunamadi!");
                        continue;
                    }

                    System.out.println("Degistirmek istemediginiz alanlari bos (Enter) geciniz.");

                    System.out.print("Yeni Miktar (Eski: " + bulunanGider.getMiktar() + "): ");
                    String miktarGiris = scanner.nextLine();
                    double yeniMiktar;
                    if (miktarGiris.isEmpty()) {
                        yeniMiktar = bulunanGider.getMiktar();
                    } else {
                        yeniMiktar = Double.parseDouble(miktarGiris);
                    }

                    System.out.print("Yeni Kategori (Eski: " + bulunanGider.getKategori() + "): ");
                    String yeniKategori = scanner.nextLine();
                    if (yeniKategori.isEmpty()) {
                        yeniKategori = bulunanGider.getKategori();
                    }

                    System.out.print("Yeni Tarih (Eski: " + bulunanGider.getTarih() + "): ");
                    String yeniTarih = scanner.nextLine();
                    if (yeniTarih.isEmpty()) {
                        yeniTarih = bulunanGider.getTarih();
                    }

                    System.out.print("Yeni Aciklama (Eski: " + bulunanGider.getAciklama() + "): ");
                    String yeniAciklama = scanner.nextLine();
                    if (yeniAciklama.isEmpty()) {
                        yeniAciklama = bulunanGider.getAciklama();
                    }

                    db.giderGuncelle(guncelleId, yeniMiktar, yeniKategori, yeniTarih, yeniAciklama);
                    System.out.println("Gider basariyla guncellendi.");

                } else if (secim.equals("4")) {
                    System.out.println("\n=== GIDER LISTESI ===");
                    ArrayList<Gider> tumGiderler = db.getGiderler();

                    if (tumGiderler.size() == 0) {
                        System.out.println("Henuz kayitli gider yok.");
                    } else {
                        for (int i = 0; i < tumGiderler.size(); i++) {
                            Gider g = tumGiderler.get(i);
                            System.out.println("ID: " + g.getId() + " | Tarih: " + g.getTarih() + " | Kategori: " + g.getKategori() + " | Miktar: " + g.getMiktar() + " TL | Aciklama: " + g.getAciklama());
                        }
                    }

                } else if (secim.equals("5")) {
                    System.out.print("Raporlanacak Kategori: ");
                    String kat = scanner.nextLine();
                    double toplam = db.kategoriyeGoreToplam(kat);
                    System.out.println(kat + " kategorisindeki toplam harcama: " + toplam + " TL");

                    // 6- CIKIS ISLEMI
                } else if (secim.equals("6")) {
                    System.out.println("Sistem kapatiliyor. Veriler kaydedildi.");
                    break;
                } else {
                    System.out.println("Gecersiz secim! 1 ile 6 arasinda bir sayi giriniz.");
                }

            } catch (Exception e) {
                System.out.println("HATA: Hatali veya gecersiz bir veri girdiniz!");
            }
        }
    }
}