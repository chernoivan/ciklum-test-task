This project is developed on the Java 8 using Maven and interacts with the database for its editing.
The application is started using the console command "mvn exec:java" in the project directory.

The user is provided with the following menu:
1. Create Product
2. Show all products
3. Show product by ID
4. Delete product by ID
5. Show the list of all products, which have been ordered at least once
6. Create order
7. Show order information by order ID
8. List of all orders 

Each action number corresponds to the action to be performed.
Application Functional Requirements:
1. Create Product
2. Create Order with a list of the products specified by id. User Id is auto generated.
3. Showing following views:

a. | Product Name | Product Price | Product Status | for all products

b. | Product Name | Product Price | Product Status | by product Id

c. List all products, which have been ordered at least once, with total ordered quantity sorted descending by the quantity. 

d. | Order ID | Products total Price | Product Name | Products Quantity in orderEntry | Order Created Date [YYYY-MM-DD HH:MM ] | by order Id

e. List of all orders using previous view

4. Remove product by ID
