package com.bank.app.accounts;

import com.bank.app.cards.KrediKarti;

public class VadesizHesap extends BankaHesabi { //kalıtım kullanımı
    private String hesapTuru;

    public VadesizHesap(double bakiye) {
        super(bakiye);
        this.hesapTuru = "Vadesiz Hesap";
    }

 //  Alıcı hesap, gönderen hesap ve miktar parametre olarak alınır
    public void paraTransferi(BankaHesabi aliciHesap, BankaHesabi gonderenHesap, double miktar) {
        if (miktar > 0 && gonderenHesap.getBakiye() >= miktar) {
            // Gönderen hesabın bakiyesinden miktar düşülür 
            gonderenHesap.setBakiye(gonderenHesap.getBakiye() - miktar);
            // Alıcı hesabın bakiyesine miktar eklenir 
            aliciHesap.setBakiye(aliciHesap.getBakiye() + miktar);
            System.out.println("Transfer Başarılı! Gönderilen: " + miktar + " TL");
        } else {
            System.out.println("HATA: Transfer için yetersiz bakiye veya geçersiz tutar!");
        }
    }

    public void krediKartiBorcOdeme(KrediKarti kart, double miktar) {
        if (miktar > 0 && this.getBakiye() >= miktar) {
            this.setBakiye(this.getBakiye() - miktar);
            kart.setGuncelBorc(kart.getGuncelBorc() - miktar);
            System.out.println("Kart borcu ödendi. Kalan Borç: " + kart.getGuncelBorc());
        } else {
            System.out.println("HATA: Ödeme için yetersiz bakiye!");
        } }
    public String getHesapTuru() { return hesapTuru; }
    public void setHesapTuru(String hesapTuru) { this.hesapTuru = hesapTuru; }

    public String toString() {
        return super.toString() + " | Hesap Türü: " + hesapTuru;
    }}