package csc112;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ECommerceSystem {
    private static final String SEP = "✦────────────────────────────────✦";
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("d-M-yyyy");

    private static CustomerDataStructure allCustomers;
    private static OrderDataStructure allOrders;
    private static ProductDataStructure allProducts;
    private static ReviewDataStructure allReviews;
    
    public ECommerceSystem() {
        allCustomers = new CustomerDataStructure();
        allOrders = new OrderDataStructure();
        allProducts = new ProductDataStructure();
        allReviews = new ReviewDataStructure();
    }

    private static void Load_all() {
        System.out.println(SEP);
        System.out.println("✦ Loading Data Files ✦");
        System.out.println(SEP);

        allCustomers.loadCustomers("customers.csv");
        allOrders.setAllCustomers(allCustomers);
        allOrders.loadOrders("orders.csv");
        allProducts.loadProducts("products.csv");
        allReviews.loadReviews("reviews.csv");
        
        allReviews.setAllCustomers(allCustomers);
        allReviews.setAllProducts(allProducts);

        System.out.println("✅ All files loaded successfully!");
        System.out.println(SEP);
    }

    public static void main(String[] args) {
        new ECommerceSystem();
        Scanner in = new Scanner(System.in);
        int mainChoice;

        System.out.println(SEP);
        System.out.println("★ E-Commerce System ★");
        System.out.println(SEP);

        do {
            System.out.println();
            System.out.println(SEP);
            System.out.println("Main Menu:");
            System.out.println("1) Products");
            System.out.println("2) Customers");
            System.out.println("3) Orders");
            System.out.println("4) Reviews");
            System.out.println("5) Load Data Files");
            System.out.println("0) Exit");
            System.out.println(SEP);
            System.out.print("Enter choice: ");
            mainChoice = in.nextInt();

            switch (mainChoice) {
                case 1: productMenu(in); break;
                case 2: customerMenu(in); break;
                case 3: orderMenu(in); break;
                case 4: reviewMenu(in); break;
                case 5: Load_all(); break;
                case 0: System.out.println("Thank you! Goodbye!"); break;
                default: System.out.println("Invalid choice!");
            }
        } while (mainChoice != 0);
        
        in.close();
    }

    private static void productMenu(Scanner in) {
        int choice;
        do {
            System.out.println(SEP);
            System.out.println("✦ Products Menu ✦");
            System.out.println(SEP);
            System.out.println("1) Add Product");
            System.out.println("2) Remove Product");
            System.out.println("3) Update Product");
            System.out.println("4) Search Product");
            System.out.println("5) Display All Products");
            System.out.println("6) Display Out-of-Stock Products");
            System.out.println("0) Back to Main Menu");
            System.out.println(SEP);
            System.out.print("Select: ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("✦ Add New Product ✦");
                    System.out.print("→ ID: "); int id = in.nextInt();
                    System.out.print("→ Name: "); String name = in.next();
                    System.out.print("→ Price: "); double price = in.nextDouble();
                    System.out.print("→ Stock: "); int stock = in.nextInt();
                    allProducts.addProduct(new ProductNode(id, name, price, stock));
                    System.out.println("✅ Product added!");
                    break;
                    
                case 2:
                    System.out.print("→ Enter Product ID to remove: ");
                    int removeId = in.nextInt();
                    allProducts.removeProduct(removeId);
                    break;
                    
                case 3:
                    System.out.print("→ Old Product ID: "); int oldId = in.nextInt();
                    System.out.print("→ New ID: "); int nid = in.nextInt();
                    System.out.print("→ New Name: "); String n = in.next();
                    System.out.print("→ New Price: "); double p = in.nextDouble();
                    System.out.print("→ New Stock: "); int s = in.nextInt();
                    allProducts.updateProduct(oldId, new ProductNode(nid, n, p, s));
                    break;
                    
                case 4:
                    System.out.print("→ Enter Product ID: ");
                    int searchId = in.nextInt();
                    ProductNode prod = allProducts.SearchProductByid(searchId);
                    if (prod != null) {
                        prod.display();
                    } else {
                        System.out.println("❌ Product not found!");
                    }
                    break;
                    
                case 5:
                    allProducts.displayAllProducts();
                    break;
                    
                case 6:
                    allProducts.displayOutOfStock();
                    break;
                    
                case 0: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static void customerMenu(Scanner in) {
        int choice;
        do {
            System.out.println(SEP);
            System.out.println("✦ Customers Menu ✦");
            System.out.println(SEP);
            System.out.println("1) Add Customer");
            System.out.println("2) Add Customer to Product");
            System.out.println("3) View Order History");
            System.out.println("4) Display All Customers");
            System.out.println("0) Back to Main Menu");
            System.out.println(SEP);
            System.out.print("Select: ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("✦ Add New Customer ✦");
                    System.out.print("→ ID: "); int id = in.nextInt();
                    System.out.print("→ Name: "); String name = in.next();
                    System.out.print("→ Email: "); String email = in.next();
                    allCustomers.addCustomer(new CustomerNode(id, name, email));
                    System.out.println("✅ Customer added!");
                    break;
                    
                case 2:
                    System.out.println("✦ Add Customer to Product ✦");
                    System.out.print("→ Customer ID: "); int cid = in.nextInt();
                    System.out.print("→ Product ID: "); int pid = in.nextInt();
                    
                    CustomerNode customer = allCustomers.searchById(cid);
                    ProductNode product = allProducts.SearchProductByid(pid);
                    
                    if (customer == null) {
                        System.out.println("❌ Customer not found!");
                    } else if (product == null) {
                        System.out.println("❌ Product not found!");
                    } else if (product.getStock() <= 0) {
                        System.out.println("❌ Product out of stock!");
                    } else {
                        System.out.print("→ Order ID: "); int oid = in.nextInt();
                        OrderNode newOrder = new OrderNode(oid, cid, String.valueOf(pid), 
                                                         product.getPrice(), LocalDate.now(), "Pending");
                        allOrders.addOrder(newOrder);
                        product.setStock(product.getStock() - 1);
                        System.out.println("✅ Customer added to product successfully!");
                    }
                    break;
                    
                case 3:
                    System.out.print("→ Enter customer ID: ");
                    int customerId = in.nextInt();
                    allCustomers.printOrderHistory(customerId);
                    break;
                    
                case 4:
                    allCustomers.displayAll();
                    break;
                    
                case 0: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static void orderMenu(Scanner in) {
        int choice;
        do {
            System.out.println(SEP);
            System.out.println("✦ Orders Menu ✦");
            System.out.println(SEP);
            System.out.println("1) Place Order");
            System.out.println("2) Update Order Status");
            System.out.println("3) Search Order by ID");
            System.out.println("4) Remove Order");
            System.out.println("5) Display All Orders");
            System.out.println("6) Display Orders Between Dates");
            System.out.println("0) Back to Main Menu");
            System.out.println(SEP);
            System.out.print("Select: ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    placeOrder(in);
                    break;
                    
                case 2:
                    System.out.print("→ Order ID: "); int oid = in.nextInt();
                    System.out.print("→ New Status: "); String status = in.next();
                    allOrders.UpdateOrderState(oid, status);
                    break;
                    
                case 3:
                    System.out.print("→ Enter Order ID: "); int searchId = in.nextInt();
                    OrderNode order = allOrders.searchOrderById(searchId);
                    if (order != null) {
                        order.display();
                    } else {
                        System.out.println("❌ Order not found!");
                    }
                    break;
                    
                case 4:
                    System.out.print("→ Enter Order ID to remove: "); int removeId = in.nextInt();
                    allOrders.removeOrder(removeId);
                    break;
                    
                case 5:
                    allOrders.displayAllOrders();
                    break;
                    
                case 6:
                    System.out.print("→ Start Date (d-M-yyyy): "); LocalDate d1 = LocalDate.parse(in.next(), DF);
                    System.out.print("→ End Date (d-M-yyyy): "); LocalDate d2 = LocalDate.parse(in.next(), DF);
                    allOrders.displayAllOrders_between2dates(d1, d2);
                    break;
                    
                case 0: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static void placeOrder(Scanner in) {
        System.out.println(SEP);
        System.out.println("✦ Place a New Order ✦");
        System.out.println(SEP);

        System.out.print("→ Order ID: "); int oid = in.nextInt();
        System.out.print("→ Customer ID: "); int cid = in.nextInt();

        StringBuilder prodIds = new StringBuilder();
        double total = 0.0;
        char more = 'y';

        while (more == 'y' || more == 'Y') {
            System.out.print("→ Product ID: "); int pid = in.nextInt();
            ProductNode p = allProducts.SearchProductByid(pid);
            
            if (p == null) {
                System.out.println("❌ Product not found!");
            } else if (p.getStock() <= 0) {
                System.out.println("⚠️ Product out of stock!");
            } else {
                if (prodIds.length() > 0) prodIds.append(';');
                prodIds.append(pid);
                total += p.getPrice();
                p.setStock(p.getStock() - 1);
                System.out.println("✅ Added: " + p.getName() + " | Price: $" + p.getPrice());
            }

            System.out.print("Add another product? (y/n): ");
            more = in.next().charAt(0);
        }

        System.out.print("→ Order Date (d-M-yyyy): ");
        LocalDate date = LocalDate.parse(in.next(), DF);

        System.out.print("→ Order Status: ");
        String status = in.next();

        OrderNode o = new OrderNode(oid, cid, prodIds.toString(), total, date, status);
        allOrders.addOrder(o);
        System.out.println("✅ Order placed successfully! Total: $" + total);
    }

    private static void reviewMenu(Scanner in) {
        int choice;
        do {
            System.out.println(SEP);
            System.out.println("✦ Reviews Menu ✦");
            System.out.println(SEP);
            System.out.println("1) Add Review");
            System.out.println("2) Update Review");
            System.out.println("3) Edit Review");
            System.out.println("4) Display All Reviews");
            System.out.println("5) Get Average Rating");
            System.out.println("6) Suggest Top 3 Products");
            System.out.println("0) Back to Main Menu");
            System.out.println(SEP);
            System.out.print("Select: ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("✦ Add New Review ✦");
                    System.out.print("→ Review ID: "); int rid = in.nextInt();
                    System.out.print("→ Product ID: "); int pid = in.nextInt();
                    System.out.print("→ Customer ID: "); int cid = in.nextInt();
                    System.out.print("→ Rating (1-5): "); int rating = in.nextInt();
                    System.out.print("→ Comment: "); in.nextLine(); String comment = in.nextLine();
                    allReviews.addReview(new ReviewNode(rid, pid, cid, rating, comment));
                    break;
                    
                case 2:
                    System.out.print("→ Old Review ID: "); int oldId = in.nextInt();
                    System.out.print("→ New Review ID: "); int newId = in.nextInt();
                    System.out.print("→ Product ID: "); int prodId = in.nextInt();
                    System.out.print("→ Customer ID: "); int custId = in.nextInt();
                    System.out.print("→ Rating (1-5): "); int rate = in.nextInt();
                    System.out.print("→ Comment: "); in.nextLine(); String com = in.nextLine();
                    allReviews.updateReview(oldId, new ReviewNode(newId, prodId, custId, rate, com));
                    break;
                    
                case 3:
                    System.out.println("✦ Edit Review ✦");
                    System.out.print("→ Review ID to edit: "); int editId = in.nextInt();
                    System.out.print("→ New Rating (1-5): "); int newRating = in.nextInt();
                    System.out.print("→ New Comment: "); in.nextLine(); String newComment = in.nextLine();
                    allReviews.editReview(editId, newRating, newComment);
                    break;
                    
                case 4:
                    allReviews.displayAllReviews();
                    break;
                    
                case 5:
                    System.out.print("→ Enter Product ID: "); int productId = in.nextInt();
                    allReviews.getAverageRating(productId);
                    break;
                    
                case 6:
                    allReviews.suggestTop3Products();
                    break;
                    
                case 0: break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }
}