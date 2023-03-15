package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.sql.*;

import static org.junit.Assert.assertEquals;

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
    }
    @Then("Database kapatilir")
    public void database_kapatilir() throws SQLException {
        connection.close();
    }

}
