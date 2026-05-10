package com.bank.app.accounts;
import java.util.Random;

public class BankaHesabi {
    private String iban;
    private double bakiye;

    public BankaHesabi(double bakiye) {
        this.iban = generateRandomIban();
        this.bakiye = bakiye;
    }  
    private String generateRandomIban() {//Rastgele IBAN üreten yardımcı metot
        java.util.Random rnd = new java.util.Random();
        StringBuilder sb = new StringBuilder("TR");
        
        for (int i = 0; i < 24; i++) {
            // i=2 TRxx sonrası boşluk ekle
            if (i == 2 || i == 6 || i == 10 || i == 14 || i == 18 || i == 22) {//StringBuilder'a ekleme yaparken belirli index'lerde boşluk bırakır. TR26 6678.... gerçek hayata benzer okunuşu rahatlatmak için düşündüm.
                	sb.append(" ");
            }
            			sb.append(rnd.nextInt(10));
        }
        				return sb.toString();
    }
    
    public String toString() {
        return "IBAN: " + iban + " | Bakiye: " + bakiye + " TL";
    }
    
    
    public String getIban() { 
    	return iban; 
    	}
    public void setIban(String iban) { 
    	this.iban = iban; 
    	}
    public double getBakiye() {
    	return bakiye; 
    	}
    public void setBakiye(double bakiye) { 
    	this.bakiye = bakiye; 
    	}
   
}