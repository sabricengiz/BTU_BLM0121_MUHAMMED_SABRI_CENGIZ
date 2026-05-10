package com.bank.app.people;

import com.bank.app.accounts.*; 	//arraylistleri oluşturmak için import edelim.
import com.bank.app.cards.KrediKarti;

import java.util.ArrayList;
import java.util.Random;

public class Musteri extends Kisi { //yavru extend ata sınıf Kalıtım gösterimi
    private String musteriNumarasi;
    private ArrayList<BankaHesabi> hesaplar; 
    private ArrayList<KrediKarti> krediKartlari;

    public Musteri(String ad, String soyad, String email, int telefonNumarasi) {
        super(ad, soyad, email, telefonNumarasi);
        this.musteriNumarasi = generateRandomMusteriNo();
        this.hesaplar = new ArrayList<>();
        this.krediKartlari = new ArrayList<>();
    }

    private String generateRandomMusteriNo() {
        Random rnd = new Random();
        return "CSTMR" + (10000 + rnd.nextInt(90000));
    }

    // Parametreye türüne göre hesabı oluşturup listeye ekleyen metod
    public void hesapEkle(String hesapTuru) {
        if (hesapTuru.equalsIgnoreCase("Vadesiz")) {// büyük/küçük harf bakmaksızın string karşılaştırıp true veya false döndürür.
            VadesizHesap yeniHesap = new VadesizHesap(0.0); // Başlangıç bakiyesi 0 
            hesaplar.add(yeniHesap); // Vadesiz veya Yatırım hesabı bağlayıp müşterinin cüzdanına (listesine) ekliyoruz.
            System.out.println("Vadesiz hesap başarıyla açıldı. IBAN: " + yeniHesap.getIban());
      } else if (hesapTuru.equalsIgnoreCase("Yatırım")) {
            YatirimHesabi yeniHesap = new YatirimHesabi(0.0);
            hesaplar.add(yeniHesap);
            System.out.println("Yatırım hesabı başarıyla açıldı. IBAN: " + yeniHesap.getIban());
      } else {
            System.out.println("Geçersiz hesap türü! 'Vadesiz' veya 'Yatırım' girmelisiniz.");
        }
    }

   
    public void krediKartiEkle(double limit) {
        KrediKarti yeniKart = new KrediKarti(limit, 0.0);  // Parametre olarak gelen limiti alıp yeni bir KrediKarti objesi türetiyor 
        krediKartlari.add(yeniKart);// ve müşterinin kartları ArrayList'ine referans olarak ekliyoruz.
        System.out.println("Kredi kartı tanımlandı. Kart No: " + yeniKart.getKartNumarasi());
    }

    // bakiye kontrollü hesap silme methodu
    public void hesapSil(BankaHesabi hesap) {
        if (hesap.getBakiye() > 0) {
            System.out.println("Lütfen öncelikle bakiyenizi başka bir hesaba aktarınız.");
        } else {
            hesaplar.remove(hesap);
            System.out.println("Hesap başarıyla silindi: " + hesap.getIban());
        }
    }

    // kartı silme işlemini kontrol ederek yapan metod
    public void krediKartiSil(KrediKarti kart) {
        if (kart.getGuncelBorc() == 0) {
            krediKartlari.remove(kart);// Kartın güncel borcu tamamen sıfırlanmışsa müşterinin listesinden siliyoruz.
            System.out.println("Kredi kartı başarıyla silindi: " + kart.getKartNumarasi());
        } else {
            System.out.println("Lütfen öncelikle borç ödemesi yapınız. Güncel Borç: " + kart.getGuncelBorc());
        }
    }
    public String toString() { // override : ata sınıftaki methodu ezerek burayı kullanır.
        return super.toString() + " | Müşteri No: " + musteriNumarasi;
    }

    public String getMusteriNumarasi() { 
    	return musteriNumarasi; 
    	}
    public void setMusteriNumarasi(String musteriNumarasi) {
    	this.musteriNumarasi = musteriNumarasi; 
    	}

    public ArrayList<BankaHesabi> getHesaplar() { 
    	return hesaplar;
    	}
    public void setHesaplar(ArrayList<BankaHesabi> hesaplar) {
    	this.hesaplar = hesaplar;
    	}

    public ArrayList<KrediKarti> getKrediKartlari() { 
    	return krediKartlari;
    	}
    public void setKrediKartlari(ArrayList<KrediKarti> krediKartlari) {
this.krediKartlari = krediKartlari; 
}

  
	}