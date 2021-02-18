package com.samurai.task.starter.requirements;

import com.samurai.task.starter.util.ConnectionManager;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductTest {

    @Test
    public void getProductByIdTest() throws SQLException {
        String name = "apple";
        String price = "3";
        String status = "OK";

        String lastId = "";
        String nameResult = "";
        String priceResult = "";
        String statusResult = "";

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        String sql = "INSERT INTO products (name, price, status, created_at) " + "\n" +
                "VALUES ('" + name + "', '" + price + "', '" + status + "', '" + currentTime + "');";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            ResultSet rs = statement.executeQuery("select last_insert_id() as last_id from products");
            if (rs.next()) {
                lastId = rs.getString("last_id");
            }
        }

        sql = "SELECT name, price, status FROM products " + "\n" +
                "WHERE id=" + lastId + ";";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                nameResult = resultSet.getString(1);
                priceResult = resultSet.getString(2);
                statusResult = resultSet.getString(3);
            }
        }

        Assert.assertEquals(name, nameResult);
        Assert.assertEquals(price, priceResult);
        Assert.assertEquals(status, statusResult);

        System.out.println("Name = " + name + "; NameResult= " + nameResult);
        System.out.println("Price = " + price + "; PriceResult= " + priceResult);
        System.out.println("Status = " + status + "; StatusResult= " + statusResult);
    }
}
