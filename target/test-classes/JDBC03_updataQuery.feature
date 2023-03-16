Feature: JDBC baglantisi uzerinden update sorgusu yapma
#  Bir yönetici olarak DataBase üzerinden customer_addresses tablosundaki
# istenen Customer'in adress bilgisini güncelleyebilmeli ve güncellenen
# adresi customer_addresses tablosunda oldugunu dogrulayabilmeliyim
  @adres
Scenario Outline:

  * Database baglantisi kurulur
  * "<id>" degeri verilen customerin "<adresi>" güncellenir.
  * customer address tablosundaki "address" bilgileri listelenir
  *  customerin "<adresi>" guncellendigi dogrulanir.
  * Database baglantisi kapatilir

  Examples:
    | id | adresi|
    |1| Yozgat|