package com.bank.app.cards;

public class KrediKarti {
    private String kartNumarasi;
    private double limit;
    private double guncelBorc;
    private double kullanilabilirLimit;

    public KrediKarti(double limit, double guncelBorc) {
        this.kartNumarasi = generateRandomCardNumber();
        this.limit = limit;
        this.guncelBorc = guncelBorc;
        this.kullanilabilirLimit = limit - guncelBorc; // Kalan limit hesaplanıyor
    }

    private String generateRandomCardNumber() {
        java.util.Random rnd = new java.util.Random();
        //4 yerine 1-9 arası rastgele bir ilk hane üretiyoruz
        StringBuilder sb = new StringBuilder(String.valueOf(rnd.nextInt(9) + 1)); 
        // İlk hane yukarıda üretildiği için döngü 1'den başlıyor
        for (int i = 1; i < 16; i++) {
            if (i % 4 == 0) { 
                sb.append(" "); // 4-4-4-4 formatı için aralara boşluk
            }
            sb.append(rnd.nextInt(10)); // Kalan 15 haneyi random dolduruyoruz.
        }
        
        return sb.toString();
    }
    public String toString() {
        return "Kart No: " + kartNumarasi + " | Limit: " + limit + " | Güncel Borç: " + guncelBorc + " | Kullanılabilir: " + kullanilabilirLimit;
    }

   
    public String getKartNumarasi() { 
    	return kartNumarasi; }
    public void setKartNumarasi(String kartNumarasi) { 
    	this.kartNumarasi = kartNumarasi; }

    public double getLimit() {
    	return limit; }
    public void setLimit(double limit) { 
        this.limit = limit; 
        this.kullanilabilirLimit = limit - this.guncelBorc;
    }
    

   	public double getGuncelBorc() { 
    	return guncelBorc; }
    
    public void setGuncelBorc(double guncelBorc) { 
        this.guncelBorc = guncelBorc; 
        // Borç değiştiğinde, kullanılabilir limit de otomatik güncellenmeli.
        this.kullanilabilirLimit = this.limit - this.guncelBorc; 
    }

    public double getKullanilabilirLimit() { 
    	return kullanilabilirLimit; }
    public void setKullanilabilirLimit(double kullanilabilirLimit) {
    	this.kullanilabilirLimit = kullanilabilirLimit; }

   
    
}
