package com.samurai.task.starter.requirements;

import com.samurai.task.starter.enums.ProductStatus;
import com.samurai.task.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Products {

    public void createProduct() throws SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write name, price and status of product");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        String price = scanner.nextLine();
        System.out.print("Status: ");
        String status = scanner.nextLine();

        while (ProductStatus.fromValue(status) == ProductStatus.valueOf("NOT_FOUND")) {
            System.out.println("Please, write correct status of product (out_of_stock, in_stock, running_low)");
            System.out.print("Status: ");
            status = scanner.nextLine();
        }

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        String sql = "INSERT INTO products (name, price, status, created_at) " + "\n" +
                "VALUES ('" + name + "', '" + price + "', '" + status + "', '" + currentTime + "');";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public void getProductById() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write ID of product");

        int productID = scanner.nextInt();

        String sql = "SELECT name, price, status FROM products " + "\n" +
                "WHERE id=" + productID + ";";

        Result(sql);
    }

    public void showAllProducts() throws SQLException {
        String sql = "SELECT name, price, status FROM products;";

        Result(sql);
    }

    public void deleteProductByID() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write ID of product");

        int productID = scanner.nextInt();

        String sql = "DELETE FROM products " + "\n" +
                "WHERE id=" + productID + ";";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Product with id " + productID + " successfully deleted!");
        }
    }

    public void totalOrderedProducts() throws SQLException {
        String sql = "SELECT product_id, Count(*) as count\n" +
                "FROM order_items\n" +
                "GROUP BY product_id\n" +
                "ORDER BY 2 DESC";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String productID = resultSet.getString(1);
                String count = resultSet.getString(2);

                System.out.println("Product: " + productID);
                System.out.println("Total ordered quantity: " + count);
                System.out.println("===================\n");
            }
        }
    }

    private void Result(String sql) throws SQLException {
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String price = resultSet.getString(2);
                String status = resultSet.getString(3);

                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Status: " + status);
                System.out.println("===================\n");
            }
        }
    }
}
