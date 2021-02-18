package com.samurai.task.starter.requirements;

import com.samurai.task.starter.util.ConnectionManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Orders {

    public void createOrder() throws SQLException {
        String orderId = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write product ID and quantity which you want add to order: ");
        System.out.println("Order status: ");
        String status = scanner.nextLine();
        System.out.print("Product ID: ");
        Integer productID = scanner.nextInt();
        System.out.print("Product quantity: ");
        Integer productQuantity = scanner.nextInt();

        java.util.Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);

        int user_id = (int) (Math.random() * 9999);

        String createOrderSql = "INSERT INTO orders (user_id, status, created_at) " +
                "VALUES ('" + user_id + "', '" + status + "', '" + currentTime + "')";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            statement.execute(createOrderSql);
        }

        String maxIdSql = "SELECT id FROM orders";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(maxIdSql);

            while (resultSet.next()) {
                orderId = resultSet.getString(1);
            }
        }

        String createOrderItemSql = "INSERT INTO order_items(order_id, product_id, quantity) " +
                "VALUES ('" + orderId + "', '" + productID + "', '" + productQuantity + "')";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            statement.execute(createOrderItemSql);
        }

        boolean isContinue = true;

        while (isContinue) {
            System.out.println("Add one more product to order? Y/N");
            Scanner scan = new Scanner(System.in);
            String isNext = scan.nextLine();
            switch (isNext) {
                case ("Y"):
                case ("y"):
                    addProduct(Integer.parseInt(orderId));
                    break;
                case ("N"):
                case ("n"):
                    isContinue = false;
                    break;
                default:
                    System.out.println("Wrong input! Try again");
            }
        }
    }

    public void showOrderById() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write ID of order");

        int orderId = scanner.nextInt();

        String sql = "SELECT orders.id, orders.created_at, order_items.product_id, order_items.quantity, products.name, order_items.quantity*products.price AS SUM " +
                "FROM orders, products,order_items " +
                "where order_items.order_id = orders.id and order_items.product_id = products.id and orders.id = " + orderId + "; ";

        result(sql);
    }

    public void showAllOrders() throws SQLException {

        String sql = "SELECT orders.id, orders.created_at, order_items.product_id, order_items.quantity, products.name, order_items.quantity*products.price AS SUM " +
                "FROM orders, products,order_items " +
                "where order_items.order_id = orders.id and order_items.product_id = products.id; ";

        result(sql);
    }

    private void addProduct(Integer orderId) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write product ID and quantity which you want add to order: ");
        System.out.print("Product ID: ");
        Integer productID = scanner.nextInt();
        System.out.print("Product quantity: ");
        Integer productQuantity = scanner.nextInt();

        String createOrderItemSql = "INSERT INTO order_items(order_id, product_id, quantity) " +
                "VALUES ('" + orderId + "', '" + productID + "', '" + productQuantity + "')";

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            statement.execute(createOrderItemSql);
        }
    }

    private void result(String sql) throws SQLException {
        int orderIDNum = 0;
        float totalSumma = 0;

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString(1)) != orderIDNum) {
                    if ((Integer.parseInt(resultSet.getString(1)) > orderIDNum) && orderIDNum != 0)
                        System.out.println("Total summa = " + totalSumma);
                    totalSumma = 0;
                    System.out.println("===========================");
                    orderIDNum = Integer.parseInt(resultSet.getString(1));
                    String orderID = resultSet.getString(1);
                    String createdAt = resultSet.getString(2);
                    String quantity = resultSet.getString(4);
                    String name = resultSet.getString(5);
                    String sum = resultSet.getString(6);

                    System.out.println("Order ID: " + orderID);
                    System.out.println("Created at: " + createdAt);
                    System.out.println("Product name: " + name + "| Quantity: " + quantity + "| Summa: " + sum);
                    totalSumma += Float.parseFloat(sum);
                } else {
                    String name = resultSet.getString(5);
                    String quantity = resultSet.getString(4);
                    String sum = resultSet.getString(6);

                    System.out.println("Product name: " + name + "| Quantity: " + quantity + "| Summa: " + sum);
                    totalSumma += Float.parseFloat(sum);
                }
            }
            System.out.println("Total summa = " + totalSumma);
        }
    }
}
