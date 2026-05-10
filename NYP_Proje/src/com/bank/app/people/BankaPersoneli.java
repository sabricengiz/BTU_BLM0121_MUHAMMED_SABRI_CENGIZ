package com.bank.app.people;

import java.util.ArrayList;
import java.util.Random;

public class BankaPersoneli extends Kisi {
    private String personelID;
    private ArrayList<Musteri> musteriler; 

    public BankaPersoneli(String ad, String soyad, String email, int telefonNumarasi) {
        super(ad, soyad, email, telefonNumarasi);
        this.personelID = generateRandomID(); //  ID türetme
        this.musteriler = new ArrayList<>(); // NullPointer Hatası almamak için listeyi başlatıyoruz.
    }

    // Rastgele ID üreten ve döndüren metod
    private String generateRandomID() {
        Random rnd = new Random();
        return "PRSNL" + (1000 + rnd.nextInt(9000));
    }
    public String getPersonelID() { 
    	return personelID; }
    public void setPersonelID(String personelID) {
    	this.personelID = personelID; }

    public ArrayList<Musteri> getMusteriler() {
    	return musteriler; }
    public void setMusteriler(ArrayList<Musteri> musteriler) { 
    	this.musteriler = musteriler; }

   
    public String toString() {
     return super.toString() + "  Personel ID: " + personelID + "  Sorumlu Olduğu Müşteri Sayısı: " + musteriler.size();
     //zaten üst methodunu Kisi sınıfında kullanmıstık ayriyetten ekleme yaptık. yani override ettik.
    }
}