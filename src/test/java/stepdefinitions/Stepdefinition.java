package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.DBUtils.*;

public class Stepdefinition {
    //JDBC (DB) testi yapılmaya baslamadan once sistem yoneticisi ile gorusulup Database
  //  bilgileri alinir
    /*
        Database baglantisi icin gerekli bilgiler.
    type: jdbc:mysql
    host/ip: 45.84.206.41
    port:3306
    database: u480337000_tlb_training
    username: u480337000_tbl_training_u
    password: pO9#4bmxU
     */
    String url= "jdbc:mysql://45.84.206.41:3306/u480337000_tlb_training";
    String username="u480337000_tbl_training_u";
    String password="pO9#4bmxU";

    //Database sistem Yoneticisinden alınan bilgiler ile bir url olusturuldu
    //username ve password String data tipinde bir variable atandi
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    List<Object> staffId=new ArrayList<>();
    List<Object> adressList=new ArrayList<>();

    @Given("Database ile iletisimi baslat")
    public void database_ile_iletisimi_baslat() throws SQLException {
       connection= DriverManager.getConnection(url,username,password);
       statement= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

       //connection objemizi olusturduk ve (url,username,password datalarini connection objesi icine koyduk)
        //olusturdugumuz connection objesini kullanarak typelari bellli bir statement create ettik

    }
    @Then("Query statement araciligi ile database gonderilir")
    public void query_statement_araciligi_ile_database_gonderilir() throws SQLException {
        String query="select * from u480337000_tlb_training.users";

        resultSet= statement.executeQuery(query);
       // statament araciligi ile DAtabase'e gonderdigimiz query  sonucunu (datayi) bir reslult sete store ettik;
    }
    @Then("Databaseden donen resultset verisi test edilir")
    public void databaseden_donen_resultset_verisi_test_edilir() throws SQLException {
        resultSet.first();
        System.out.println(resultSet.getString("first_name"));
        String actualName=resultSet.getString("first_name");
        String expectedName="Super";
        assertEquals(expectedName,actualName);
        resultSet.next();
        System.out.println(resultSet.getString("first_name"));

        resultSet.next();
        System.out.println(resultSet.getString("first_name"));

        resultSet.absolute(11);
        System.out.println(resultSet.getString("first_name"));

        System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°");

        resultSet.absolute(0);

        int sira=1;
        while(resultSet.next()){
            System.out.println(sira+"--"+resultSet.getString("first_name"));
            sira++;
        }

        resultSet.absolute(11);
        System.out.println(resultSet.getString("email"));
        System.out.println(resultSet.getString("phone"));
        System.out.println(resultSet.getString("username"));
    }
    @Then("Database kapatilir")
    public void database_kapatilir() throws SQLException {
        connection.close();
    }
    //==========================================================================

    @Given("Database baglantisi kurulur")
    public void database_baglantisi_kurulur() {
       createConnection();
    }
    @Given("Staff tablosundaki {string} leri listelenir")
    public void staff_tablosundaki_leri_listelenir(String id) {
        String query="SELECT * FROM u480337000_tlb_training.staff";
        staffId=getColumnData("SELECT * FROM u480337000_tlb_training.staff",id);
        System.out.println(staffId);


    }
    @Given("Verilen {string} dogrulanir.")
    public void verilen_dogrulanir(String verilenId) {
        /*for (int i = 0; i <staffId.size() ; i++) {
            assertTrue(staffId.get(i).toString().equals(verilenId));
        }
        bak buna duzenleme bak
         */
      assertTrue(staffId.toString().contains(verilenId));
    }
    @Given("Database baglantisi kapatilir")
    public void database_baglantisi_kapatilir() {
        closeConnection();
    }

    //=====================================================================
    @Given("{string} degeri verilen customerin {string} güncellenir.")
    public void degeri_verilen_customerin_güncellenir(String id, String adres) throws SQLException {
        String query= "UPDATE u480337000_tlb_training.customer_addresses" +
                "SET address= '"+adres+"' WHERE id="+id;
              /*
              UPDATE u480337000_tlb_training.customer_addresses
                SET address= 'kadiköy' WHERE id=1
               */
        System.out.println(query);

        update(query);

    }
    @Given("customer address tablosundaki {string} bilgileri listelenir")
    public void customer_address_tablosundaki_bilgileri_listelenir(String columnName) {
        String query="SELECT * FROM u480337000_tlb_training.customer_addresses;";

      adressList=  getColumnData(query,columnName);
        System.out.println(adressList);
    }
    @Given("customerin {string} guncellendigi dogrulanir.")
    public void customerin_guncellendigi_dogrulanir(String guncellenenAdress) {
        assertTrue(adressList.toString().contains(guncellenenAdress));
    }

    @Given("Verilen datalar ile query hazirlanip sorgu gerceklestirilir")
    public void verilen_datalar_ile_query_hazirlanip_sorgu_gerceklestirilir() throws SQLException {
        String query="select email from u480337000_tlb_training.users where first_name ='admin' and last_name='user';";
        resultSet=getStatement().executeQuery(query);
    }
    @Given("Donen Result set datasi dogrulanir")
    public void donen_result_set_datasi_dogrulanir() throws SQLException {
            resultSet.first();

            String actualEmailData=resultSet.getString("email");
        System.out.println("beklenen  :"+resultSet.getString("email"));
            String expectedEmailData="admin@gmail.com";
            assertEquals(expectedEmailData,actualEmailData);

    }




}
