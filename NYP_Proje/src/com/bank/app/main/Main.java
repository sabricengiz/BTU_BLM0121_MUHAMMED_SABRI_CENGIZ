package com.bank.app.main;

import java.util.Scanner;
import java.util.Random;
// tüm her şeyi import ettik
import com.bank.app.accounts.*;
import com.bank.app.cards.*;
import com.bank.app.people.*;
import com.bank.app.service.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Asıl sistem olan BankaService'yi çağıralım.
        BankaService bankaServisi = new BankaService();
        BankaPersoneli personel = new BankaPersoneli("Arda", "Albayrak", "arda@banka.com", 5551632);
        bankaServisi.personelEkle(personel);
        
        Musteri aktifMusteri = null;

        System.out.println("* BANKA OTOMASYON SİSTEMİ *");

        boolean cikis = false;
        while (!cikis) {
        	System.out.println("\n--- İŞLEM MENÜSÜ ---");
            System.out.println("1. Yeni Müşteri Oluştur");
            System.out.println("2. Müşteri İçin Hesap Aç");
            System.out.println("3. Hesaba Para Ekle / Çek");
            System.out.println("4. Hesaplar Arası Para Transferi ");
            System.out.println("5. Kredi Kartı Tanımla");
            System.out.println("6. Kredi Kartı Borcu Öde");
            System.out.println("7. Hesap Sil ");
            System.out.println("8. Kredi Kartı Sil");
            System.out.println("9. Sistem ve Personel Özeti");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");
            
            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {//menü yapısı için switch-case yapısı
                case 1:     // Müşteri oluşturma ve personele atama.
                    System.out.print("Müşteri Adı: "); 
                    String ad = scanner.nextLine();
                    System.out.print("Müşteri Soyadı: "); 
                    String soyad = scanner.nextLine();
                    aktifMusteri = new Musteri(ad, soyad, ad.toLowerCase() + "@mail.com", 5550000);
                    bankaServisi.musteriEkle(aktifMusteri);
                    bankaServisi.personeleMusteriAta(personel, aktifMusteri);
                    System.out.println("SİSTEM: " + ad + " " + soyad + " adına müşteri kaydı yapıldı.");
                    break;

                case 2:     //müşteriye hesap türünü parametre alarak hesap açma
                    if (aktifMusteri == null) break;
                    System.out.print("Açılacak tür (Vadesiz/Yatırım): ");
                    // run-time hatası almamak için Küçük/büyük harf duyarlılığını ortadan kaldırdık
                    String t = scanner.nextLine().toUpperCase(); 
                    aktifMusteri.hesapEkle(t.startsWith("V") ? "Vadesiz" : "Yatırım"); //kullanıcının girdiği değeri startsWith("V") ile kontrol ediyordum.
                    // Test sırasında "vadesiz" (küçük v ile) girdiğimde sistem bunu algılayamıyor ve varsayılan olarak "Yatırım Hesabı" açarak zincirleme bir mantık hatasına sebep oluyordu.
                    break;

                case 3: 
                    if (aktifMusteri == null || aktifMusteri.getHesaplar().isEmpty()) {
                        System.out.println("HATA: İşlem yapılacak hesap bulunamadı!");//para yatırmak istemek ama hesabın bulunmaması.
                        break;
                    }
                    
                    System.out.println("İşlem Türü Seçin: [1] Para Ekle | [2] Para Çek");
                    System.out.print("Seçiminiz: ");
                    int islemTuru = scanner.nextInt();
                    
                    System.out.println("\n--- Mevcut Hesaplarınız ---");
                    // müşterinin hesaplarını dinamik olarak listeledim.
                    for (int i = 0; i < aktifMusteri.getHesaplar().size(); i++) {
                        BankaHesabi h = aktifMusteri.getHesaplar().get(i);
                        String hTuru = (h instanceof VadesizHesap) ? "Vadesiz Hesap" : "Yatırım Hesabı";
                        System.out.println((i + 1) + ". " + hTuru + " | Bakiye: " + h.getBakiye() + " TL");
                    }
                    System.out.print("İşlem yapılacak hesabı seçin (1, 2...): ");
                    int secilenIndex = scanner.nextInt();
                    
                    if (secilenIndex < 1 || secilenIndex > aktifMusteri.getHesaplar().size()) {
                        System.out.println("HATA: Geçersiz hesap seçimi!");
                        break;
                    }
                    // Kullanıcının seçtiği hesabı listeden çekiyoruz.
                    BankaHesabi secilenHesap = aktifMusteri.getHesaplar().get(secilenIndex - 1);
                    
                    System.out.print("Miktar: ");
                    double islemMiktari = scanner.nextDouble();
                    
                    if (islemTuru == 1) { 
                        if (secilenHesap instanceof YatirimHesabi) {
                            ((YatirimHesabi) secilenHesap).paraEkle(islemMiktari);
                        } else {
                            secilenHesap.setBakiye(secilenHesap.getBakiye() + islemMiktari);
                            System.out.println("SİSTEM: " + islemMiktari + " TL Vadesiz hesaba eklendi. Yeni Bakiye: " + secilenHesap.getBakiye());
                        }
                    } else if (islemTuru == 2) { 
                        if (secilenHesap instanceof YatirimHesabi) {
                            ((YatirimHesabi) secilenHesap).paraCek(islemMiktari); 
                        } else {
                            if (secilenHesap.getBakiye() >= islemMiktari) {
                                secilenHesap.setBakiye(secilenHesap.getBakiye() - islemMiktari);
                                System.out.println("SİSTEM: " + islemMiktari + " TL Vadesiz hesaptan çekildi. Yeni Bakiye: " + secilenHesap.getBakiye());
                            } else {
                                System.out.println("HATA: Yetersiz bakiye!");
                            }
                        }
                    } else {
                        System.out.println("HATA: Geçersiz işlem türü!");
                    }
                    break;

                case 4: 
                    if (aktifMusteri == null || aktifMusteri.getHesaplar().size() < 2) { 
                        System.out.println("HATA: Transfer için en az 2 hesap gereklidir!"); break; 
                    }
                    // küçük harf girilirse hata vermemesi için toUpperCase kullanımı ekledim.
                    System.out.print("Kaynak Hesap (Vadesiz/Yatırım): "); 
                    String kaynak = scanner.nextLine().toUpperCase();
                    System.out.print("Hedef Hesap (Vadesiz/Yatırım): "); 
                    String hedef = scanner.nextLine().toUpperCase();
                    System.out.print("Miktar: "); double tutar = scanner.nextDouble();

                    BankaHesabi gonderen = null; BankaHesabi alici = null;
                    VadesizHesap metodSahibi = null;

                    for(BankaHesabi h : aktifMusteri.getHesaplar()) {
                        if(kaynak.startsWith("V") && h instanceof VadesizHesap) gonderen = h;
                        if(kaynak.startsWith("Y") && h instanceof YatirimHesabi) gonderen = h;
                        if(hedef.startsWith("V") && h instanceof VadesizHesap) alici = h;
                        if(hedef.startsWith("Y") && h instanceof YatirimHesabi) alici = h;
                        if(h instanceof VadesizHesap) metodSahibi = (VadesizHesap)h;
                    }

                    if(gonderen != null && alici != null) { //transfer kısmındaki 3 parametre ve en son güncel bakiyeler.
                        metodSahibi.paraTransferi(alici, gonderen, tutar);
                        System.out.println("-> Kaynak Hesabın Güncel Bakiyesi: " + gonderen.getBakiye() + " TL");
                        System.out.println("-> Hedef Hesabın Güncel Bakiyesi: " + alici.getBakiye() + " TL");
                    } else {
                        System.out.println("HATA: Kaynak veya hedef hesap bulunamadı!");
                    }
                    break;
                    
                case 5: 
                    if (aktifMusteri == null) {
                    	System.out.println("Önce müşteri oluşturunuz!"); 
                    	break; }
                    System.out.print("Kredi Kartı Limiti ne kadar olsun?: ");
                    double limit = scanner.nextDouble();
                    aktifMusteri.krediKartiEkle(limit);
                    
                    // Rastgele ve dinamik borç atama algoritması ; borç ödeme kısmından önce kart tanımlanırken limitten daha küçük test borcu yansıtıyorum.
                    KrediKarti eklenenKart = aktifMusteri.getKrediKartlari().get(aktifMusteri.getKrediKartlari().size() - 1);
                    java.util.Random rastgele = new java.util.Random();
                    // Limit aşımını önlemek için 1 ile (limit-1) arası rastgele borç
                    double testBorcu = rastgele.nextInt((int) limit - 1) + 1; 
                    
                    eklenenKart.setGuncelBorc(testBorcu); 
                    System.out.println("SİSTEM: Test işlemleri için karta " + testBorcu + " TL yapay borç yansıtıldı.");
                    break;

                case 6:
                    if (aktifMusteri == null || aktifMusteri.getKrediKartlari().isEmpty()) { 
                        System.out.println("HATA: Önce 5. menüden kredi kartı tanımlamalısınız!"); break; 
                    }
                    
           //  Vadesiz hesap var mı kontrolü, kontrol sağlandıktan sonra borç ödeyebilme mantığıyla yaptım.
                    VadesizHesap odemeHesabi = null;
                    for (BankaHesabi h : aktifMusteri.getHesaplar()) {
                        if (h instanceof VadesizHesap) {
                            odemeHesabi = (VadesizHesap) h;
                            break; // Hesabı bulduğu an aramayı durdurur.
                        }
                    }

                    if (odemeHesabi != null) {
                        System.out.println("Güncel Borcunuz: " + aktifMusteri.getKrediKartlari().get(0).getGuncelBorc() + " TL");
                        System.out.print("Ödenecek Borç Miktarı: ");
                        double odemeTutari = scanner.nextDouble();
                        odemeHesabi.krediKartiBorcOdeme(aktifMusteri.getKrediKartlari().get(0), odemeTutari);
                    } else {
                        System.out.println("HATA: Kredi kartı borcu ödemek için sistemde bir 'Vadesiz Hesap' açmış olmanız gereklidir!");
                    }
                    break;
                case 7: 
                    if (aktifMusteri == null || aktifMusteri.getHesaplar().isEmpty()) break;
                    BankaHesabi hSil = aktifMusteri.getHesaplar().get(0);
                    
                    if (hSil.getBakiye() > 0) {
                        double bak = hSil.getBakiye();
                        System.out.println("SİSTEM: " + bak + " TL bakiye başarıyla istenilen diğer hesaba aktarıldı.");
                        hSil.setBakiye(0); 
                    }
                    aktifMusteri.hesapSil(hSil);
                    break;

                case 8:
                    if (aktifMusteri == null || aktifMusteri.getKrediKartlari().isEmpty()) break;
                    aktifMusteri.krediKartiSil(aktifMusteri.getKrediKartlari().get(0));
                    break;

                case 9:
                    System.out.println("\n========== SİSTEM VE MÜŞTERİ ÖZETİ ==========");
                    System.out.println("-> GÖREVLİ PERSONEL BİLGİSİ:");
                    System.out.println(personel.toString()); 
                  //BankaService classındaki kalan methodlar burada kullanılmıştır
                    bankaServisi.tumMusterileriListele(); //BankaServicede kullanılan arraylistler burada işe yaradı.
                 // tek tek getter çağırma yapmadan objelerin tüm state'ini ekrana basıyoruz.
                    if (aktifMusteri != null) {
                        System.out.println("\n-> AKTİF MÜŞTERİ BİLGİSİ:");
                        System.out.println(aktifMusteri.toString());   //müşteri sınıfındaki toString methodu Kişi sınıfındaki methodu ezmekte.
                        
                        System.out.println("\n-> MÜŞTERİ HESAPLARI:");
                        if (aktifMusteri.getHesaplar().isEmpty()) {
                            System.out.println("Kayıtlı hesap bulunmamaktadır.");
                        } else {
                            for (BankaHesabi h : aktifMusteri.getHesaplar()) {
                                System.out.println("- " + h.toString()); 
                            }
                        }
                        
                        System.out.println("\n-> KREDİ KARTLARI:");
                        if (aktifMusteri.getKrediKartlari().isEmpty()) {
                            System.out.println("Kayıtlı kredi kartı bulunmamaktadır.");
                        } else {
                            for (KrediKarti k : aktifMusteri.getKrediKartlari()) {
                                System.out.println("- " + k.toString()); 
                            }
                        }
                    } else {
                        System.out.println("\nSistemde henüz aktif bir müşteri yok. Lütfen 1. menüden oluşturun.");
                    }
                    System.out.println("=============================================");
                    break;
                    
                case 0:
                    cikis = true;
                    System.out.println("Sistem kapatılıyor...");
                    break;

                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
        scanner.close();
    }
}