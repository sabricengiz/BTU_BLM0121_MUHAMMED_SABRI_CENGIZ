package com.bank.app.accounts;

public class YatirimHesabi extends BankaHesabi { //kalıtım kullanımı
    private String hesapTuru;

    public YatirimHesabi(double bakiye) {
        super(bakiye);
        this.hesapTuru = "Yatırım Hesabı";
    }
    public void paraEkle(double miktar) {
        if (miktar > 0) {
            this.setBakiye(this.getBakiye() + miktar);
            System.out.println(miktar + " TL hesaba eklendi. Yeni Bakiye: " + this.getBakiye());
        }
    }

    public void paraCek(double miktar) {
        if (miktar > 0 && this.getBakiye() >= miktar) {
            this.setBakiye(this.getBakiye() - miktar);
            System.out.println(miktar + " TL çekildi. Kalan Bakiye: " + this.getBakiye());
        } else {
            System.out.println("HATA: Yetersiz bakiye!");
        }
    }
    public String toString() {
        return super.toString() + " | Hesap Türü: " + hesapTuru;
}
              public String getHesapTuru() {
            	  return hesapTuru; 
            	  }
        public void setHesapTuru(String hesapTuru) {
        	this.hesapTuru = hesapTuru; 
        }       	}
    