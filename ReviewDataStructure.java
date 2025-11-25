package datastructer;

import java.io.File;
import java.util.Scanner;

public class ReviewDataStructure {

    private BSTMap<Integer, ReviewNode> reviews;
    private ProductDataStructure allProducts;
    private CustomerDataStructure allCustomers;

    public ReviewDataStructure() {
        reviews = new BSTMap<>();
        allProducts = new ProductDataStructure();
        allCustomers = new CustomerDataStructure();
    }

    public ReviewNode SearchReviewByid(int id) {
        if (reviews.empty())
            return null;

        boolean found = reviews.find(id);
        if (found)
            return reviews.retrieve();

        return null;
    }

    public void addReview(ReviewNode r) {
        boolean inserted = reviews.insert(r.getReviewId(), r);
        if (inserted) {
            assignToProduct(r);
            assignToCustomer(r);
            System.out.println(" Review added successfully!");
        } else {
            System.out.println(" Review with ID " + r.getReviewId() + " already exists!");
        }
    }

    public void updateReview(int id, ReviewNode p) {
        ReviewNode old = SearchReviewByid(id);
        if (old == null)
            System.out.println(" Review does not exist!");
        else {
            old.UpdateReview(p);
            System.out.println(" Review updated successfully!");
        }
    }

    public void editReview(int reviewId, int newRating, String newComment) {
        ReviewNode review = SearchReviewByid(reviewId);
        if (review == null) {
            System.out.println(" Review not found!");
        } else {
            review.setRating(newRating);
            review.setComment(newComment);
            System.out.println(" Review edited successfully!");
        }
    }

    public void displayAllReviews() {
        System.out.println("✦ All Reviews ✦");

        if (reviews.empty()) {
            System.out.println(" No reviews exist");
            return;
        }

        inOrder_display(reviews.getRoot());
    }

    private void inOrder_display(BSTnode<Integer, ReviewNode> p) {
        if (p == null) return;

        inOrder_display(p.left);

        ReviewNode r = p.data;
        r.display();
        System.out.println("*****************************");

        inOrder_display(p.right);
    }

    public static ReviewNode convertStringToReview(String Line) {
        String[] a = Line.split(",", 5);

        ReviewNode r = new ReviewNode(
                Integer.parseInt(a[0].trim()),
                Integer.parseInt(a[1].trim()),
                Integer.parseInt(a[2].trim()),
                Integer.parseInt(a[3].trim()),
                a[4].trim()
        );

        return r;
    }

    public void loadReviews(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);

            System.out.println(" Reading file: " + fileName);
            System.out.println("✦────────────────────────────────✦");

            if (read.hasNextLine()) read.nextLine();

            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (!line.isEmpty()) {
                    ReviewNode r = convertStringToReview(line);
                    addReview(r);
                }
            }

            read.close();
            System.out.println("✦────────────────────────────────✦");
            System.out.println(" Reviews loaded successfully!");

        } catch (Exception e) {
            System.out.println(" Error loading reviews: " + e.getMessage());
        }
    }

    private void assignToProduct(ReviewNode r) {
        if (allProducts != null) {
            ProductNode p = allProducts.SearchProductByid(r.getProductId());
            if (p == null)
                System.out.println(" Product " + r.getProductId() + " not found for review " + r.getReviewId());
            else
                p.addReview(r);
        }
    }

    private void assignToCustomer(ReviewNode r) {
        if (allCustomers != null) {
            CustomerNode c = allCustomers.searchById(r.getCustomerId());
            if (c != null)
                c.addReview(r);
        }
    }

    public void getAverageRating(int productId) {
        ProductNode product = allProducts.SearchProductByid(productId);
        if (product == null) {
            System.out.println(" Product not found!");
            return;
        }

        double avgRating = product.getAverageRating();
        System.out.println("✦ Average Rating for " + product.getName() + " ✦");
        System.out.println("✦────────────────────────────────✦");
        System.out.println("⭐ Average Rating: " + String.format("%.2f", avgRating) + "/5");

        if (avgRating == 0) {
            System.out.println(" No reviews yet");
        } else {
            String stars = "⭐".repeat((int) avgRating) + "☆".repeat(5 - (int) avgRating);
            System.out.println(" " + stars + " (" + String.format("%.2f", avgRating) + ")");
        }

        System.out.println("✦────────────────────────────────✦");
    }

    public void suggestTop3Products() {
        System.out.println(" Top 3 Products by Rating ");
        System.out.println("✦────────────────────────────────✦");

        BSTMap<Integer, ProductNode> tree = allProducts.getBST();

        if (tree == null || tree.empty()) {
            System.out.println(" No products available");
            return;
        }

        int count = countNodes(tree.getRoot());
        ProductNode[] arr = new ProductNode[count];

        fillProducts(tree.getRoot(), arr, new int[]{0});

        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (arr[j].getAverageRating() < arr[j + 1].getAverageRating()) {
                    ProductNode temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        int limit = Math.min(3, count);
        for (int i = 0; i < limit; i++) {
            ProductNode p = arr[i];
            double avg = p.getAverageRating();
            String stars = "⭐".repeat((int) avg) + "☆".repeat(5 - (int) avg);

            System.out.println(
                    (i + 1) + ". " + p.getName() +
                            " (ID: " + p.getProductId() + ")" +
                            " - Rating: " + String.format("%.2f", avg) +
                            " " + stars
            );
        }

        System.out.println("✦────────────────────────────────✦");
    }

    private int countNodes(BSTnode<Integer, ProductNode> p) {
        if (p == null) return 0;
        return 1 + countNodes(p.left) + countNodes(p.right);
    }

    private void fillProducts(BSTnode<Integer, ProductNode> p, ProductNode[] arr, int[] idx) {
        if (p == null) return;

        fillProducts(p.left, arr, idx);
        arr[idx[0]++] = p.data;
        fillProducts(p.right, arr, idx);
    }

    public void setAllCustomers(CustomerDataStructure cs) {
        this.allCustomers = cs;
    }

    public void setAllProducts(ProductDataStructure ps) {
        this.allProducts = ps;
    }
}
