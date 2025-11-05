
package datastructer;

import java.io.File;
import java.util.Scanner;

public class ProductNode {
    private int productId;
    private String name;
    private double price;
    private int stock;
    private LinkedList<ReviewNode> reviews;

    public ProductNode(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reviews = new LinkedList<>();
    }

    public void UpdateProduct(ProductNode p) {
        this.productId = p.productId;
        this.name = p.name;
        this.price = p.price;
        this.stock = p.stock;
        this.reviews = p.reviews;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    public void addReview(ReviewNode review) {        
        reviews.addLast(review);
    }

    public double getAverageRating() {
        if (reviews.empty()) return 0.0;
        reviews.findFirst();
        double sum = 0;
        int count = 0;
        while (true) {
            sum += reviews.retrieve().getRating();
            count++;
            if (reviews.last()) break;
            reviews.findNext();
        }
        return sum / count;
    }

    public void displayReviews() {
        System.out.println("Reviews for " + name + ":");
        if (reviews.empty()) {
            System.out.println("  No reviews yet.");
        } else {
            reviews.findFirst();
            while (true) {
                reviews.retrieve().display();
                if (reviews.last()) 
                    break;
                reviews.findNext();
            }
        }
    }

    public void display() {
        System.out.println("Product ID: " + productId);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + stock);
        System.out.println("Average Rating: " + String.format("%.2f", getAverageRating()));
        System.out.println("---------------------------------");
    }
}
