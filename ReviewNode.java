/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datastructer;


import java.io.File;
import java.util.Scanner;

public class ReviewNode {
    private int reviewId;
    private int productId;    // link to product
    private int rating;       // 1..5
    private int customerId;   // link to customer
    private String comment;

    public ReviewNode(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }

    public void UpdateReview(ReviewNode p) {
        this.reviewId = p.reviewId;
        this.productId = p.productId;
        this.customerId = p.customerId;
        this.rating = p.rating;
        this.comment = p.comment;
    }

    public int getReviewId() { return reviewId; }
    public int getProductId() { return productId; }
    public int getCustomerId() { return customerId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    public void setRating(int r) { this.rating = r; }
    public void setComment(String c) { this.comment = c; }

    public void display() {
        System.out.println("Review ID: " + reviewId);
        System.out.println("Product ID: " + productId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Rating: " + rating + "/5");
        System.out.println("Comment: " + comment);
        System.out.println("---------------------------------");
    }
}
