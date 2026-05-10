package com.bank.app.service;

import com.bank.app.people.BankaPersoneli;
import com.bank.app.people.Musteri;
import java.util.ArrayList;  //ArrayList müşteri ve personelleri tutmak için import ettim

/*
 * BankaService Sınıfı:
 * UML'de nesnelerin (Hesap, Kart) kendi iç operasyonları tanımlandığı için, 
 * bu servis sınıfı bankanın genel işleyişini, müşteri-personel ilişkilerini 
 * ve sistemdeki genel kayıtları yönetmek üzere tasarlanmıştır.
 */
public class BankaService {
    private ArrayList<Musteri> sistemdekiMusteriler;  // Sisteme kayıtlı tüm verileri tutan listeler
    private ArrayList<BankaPersoneli> sistemdekiPersoneller;

    public BankaService() {
        this.sistemdekiMusteriler = new ArrayList<>();
        this.sistemdekiPersoneller = new ArrayList<>();
    }

    public void musteriEkle(Musteri musteri) {
        sistemdekiMusteriler.add(musteri);
        System.out.println("SİSTEM : " + musteri.getAd() + " " + musteri.getSoyad() + " banka sistemine kaydedildi.");
    }

    public void personelEkle(BankaPersoneli personel) {
        sistemdekiPersoneller.add(personel);
        System.out.println("SİSTEM : Personel " + personel.getAd() + " " + personel.getSoyad() + " sisteme kaydedildi.");
    }

    public void personeleMusteriAta(BankaPersoneli personel, Musteri musteri) {
        // Personelin kendi listesine müşteriyi ekliyoruz
        personel.getMusteriler().add(musteri);
        System.out.println("SİSTEM : " + musteri.getMusteriNumarasi() + " numaralı müşteri, "  + personel.getAd() + " isimli temsilciye başarıyla atandı.");
    }

    // RAPORLAMA
    public void tumMusterileriListele() {
        System.out.println("\n--- BANKA SİSTEMİNDEKİ TÜM MÜŞTERİLER ---");
        if (sistemdekiMusteriler.isEmpty()) {
            System.out.println("Sistemde henüz kayıtlı müşteri bulunmamaktadır.");
        } else {
            for (Musteri m : sistemdekiMusteriler) {// foreach döngüsüyle dönüp kısa özetleri terminale basıyoruz.
                System.out.println("- Müşteri No: " + m.getMusteriNumarasi() + " | İsim: " + m.getAd() + " " + m.getSoyad());
            }}}}