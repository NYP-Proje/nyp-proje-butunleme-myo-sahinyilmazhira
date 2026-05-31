package Ntp;

import java.io.*;

public class GuvenlikServisi {
    private String dosyaAdi = "kullanicilar.txt";


    public GuvenlikServisi() {
        File f = new File(dosyaAdi);
        if (f.exists()) {
            f.delete();
        }
    }

    public boolean kayitliKullaniciVarMi() {
        File f = new File(dosyaAdi);
        if (!f.exists() || f.length() == 0) {
            return false;
        }
        return true;
    }

    public void kayitOl(String kAdi, String sifre) {
        try {
            PrintWriter yazici = new PrintWriter(new FileWriter(dosyaAdi));
            yazici.println(kAdi + ";" + sifre);
            yazici.close();
        } catch (Exception e) {
            System.out.println("Kayit esnasinda hata olustu!");
        }
    }

    public boolean girisYap(String kAdi, String sifre) {
        try {
            File f = new File(dosyaAdi);
            if (!f.exists()) return false;

            BufferedReader okuyucu = new BufferedReader(new FileReader(f));
            String satir = okuyucu.readLine();
            okuyucu.close();

            if (satir != null) {
                String[] parcalar = satir.split(";");
                if (parcalar.length == 2) {
                    return parcalar[0].equals(kAdi) && parcalar[1].equals(sifre);
                }
            }
        } catch (Exception e) {
            System.out.println("Giris kontrolu yapilirken hata olustu!");
        }
        return false;
    }
}