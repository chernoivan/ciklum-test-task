package com.samurai.task.starter;

import com.samurai.task.starter.requirements.Orders;
import com.samurai.task.starter.requirements.Products;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;

        boolean isContinue = true;

        ChoseAction();

        while (isContinue) {
            System.out.println("Continue? Y/N");
            Scanner scanner = new Scanner(System.in);
            String isNext = scanner.nextLine();
            switch (isNext) {
                case ("Y"):
                case ("y"):
                    ChoseAction();
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

    private static void ChoseAction() throws SQLException {
        Products products = new Products();
        Orders orders = new Orders();

        System.out.println("Chose one of actions for star:");
        System.out.println("1. Create Product");
        System.out.println("2. Show all products");
        System.out.println("3. Show product by ID");
        System.out.println("4. Delete product by ID");
        System.out.println("5. Show list all products, which have been ordered at least once");
        System.out.println("6. Create order");
        System.out.println("7. Show order information by order ID");
        System.out.println("8. List of all orders");

        System.out.println("Action №: ");
        Scanner scanner = new Scanner(System.in);
        int task = scanner.nextInt();

        switch (task) {
            case (1):
                products.createProduct();
                break;
            case (2):
                products.showAllProducts();
                break;
            case (3):
                products.getProductById();
                break;
            case (4):
                products.deleteProductByID();
                break;
            case (5):
                products.totalOrderedProducts();
                break;
            case (6):
                orders.createOrder();
                break;
            case (7):
                orders.showOrderById();
                break;
            case (8):
                orders.showAllOrders();
                break;
            default:
                System.out.print("Wrong action. Try again");
        }
    }
}

