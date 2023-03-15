Feature: JDBC uzerinden Database baglantisi kurularak ilk test
  @jdbc
  Scenario: Users tablosundan isimler testi.

    Given Database ile iletisimi baslat
    Then Query statement araciligi ile database gonderilir
    Then Databaseden donen resultset verisi test edilir
    And  Database kapatilir
