package csc112;

import java.io.File;
import java.util.Scanner;

public class ReviewDataStructure {
    private LinkedList<ReviewNode> reviews;
    private ProductDataStructure allProducts;
    private CustomerDataStructure allCustomers;

    public ReviewDataStructure() {
        reviews = new LinkedList<>();
        allProducts = new ProductDataStructure();
        allCustomers = new CustomerDataStructure();
    }

    public ReviewNode SearchReviewByid(int id) {
        if (reviews.empty()) return null;
        
        reviews.findFirst();
        while (true) {
            if (reviews.retrieve().getReviewId() == id)
                return reviews.retrieve();
            if (reviews.last()) break;
            reviews.findNext();
        }
        return null;
    }

    public void addReview(ReviewNode r) {
        if (SearchReviewByid(r.getReviewId()) == null) {
            reviews.insert(r);
            assignToProduct(r);
            System.out.println("✅ Review added successfully!");
        } else {
            System.out.println("❌ Review with ID " + r.getReviewId() + " already exists!");
        }
    }

    public void updateReview(int id, ReviewNode p) {
        ReviewNode old = SearchReviewByid(id);
        if (old == null)
            System.out.println("❌ Review does not exist!");
        else {
            old.UpdateReview(p);
            System.out.println("✅ Review updated successfully!");
        }
    }

    // ⭐ دالة جديدة: Edit Review
    public void editReview(int reviewId, int newRating, String newComment) {
        ReviewNode review = SearchReviewByid(reviewId);
        if (review == null) {
            System.out.println("❌ Review not found!");
        } else {
            review.setRating(newRating);
            review.setComment(newComment);
            System.out.println("✅ Review edited successfully!");
        }
    }

    public void displayAllReviews() {
        System.out.println("✦ All Reviews ✦");
        if (reviews.empty()) {
            System.out.println("📭 No reviews exist");
            return;
        }
        
        reviews.findFirst();
        while (true) {
            ReviewNode p = reviews.retrieve();
            p.display();
            if (reviews.last()) break;
            reviews.findNext();
        }
    }

    public static ReviewNode convertStringToReview(String Line) {
        String[] a = Line.split(",", 5);
        ReviewNode r = new ReviewNode(
            Integer.parseInt(a[0].trim()),
            Integer.parseInt(a[1].trim()),
            Integer.parseInt(a[2].trim()),
            Integer.parseInt(a[3].trim()),
            a[4]
        );
        return r;
    }

    public void loadReviews(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);
            System.out.println("📁 Reading file: " + fileName);
            System.out.println("✦────────────────────────────────✦");
            if (read.hasNextLine()) read.nextLine();

            int loadedCount = 0;
            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (line.isEmpty()) continue;
                ReviewNode r = convertStringToReview(line);
                addReview(r);
                loadedCount++;
            }

            read.close();
            System.out.println("✅ Reviews loaded: " + loadedCount);
            System.out.println("✦────────────────────────────────✦");
        } catch (Exception e) {
            System.out.println("❌ Error loading reviews: " + e.getMessage());
        }
    }

    private void assignToProduct(ReviewNode r) {
        ProductNode p = allProducts.SearchProductByid(r.getProductId());
        if (p == null)
            System.out.println("❌ Product " + r.getProductId() + " not found for review " + r.getReviewId());
        else
            p.addReview(r);
    }

    // ⭐ دالة جديدة: Get Average Rating for a Product
    public void getAverageRating(int productId) {
        ProductNode product = allProducts.SearchProductByid(productId);
        if (product == null) {
            System.out.println("❌ Product not found!");
            return;
        }

        double avgRating = product.getAverageRating();
        System.out.println("✦ Average Rating for " + product.getName() + " ✦");
        System.out.println("✦────────────────────────────────✦");
        System.out.println("⭐ Average Rating: " + String.format("%.2f", avgRating) + "/5");
        
        if (avgRating == 0) {
            System.out.println("📭 No reviews yet");
        } else {
            String stars = "⭐".repeat((int)avgRating) + "☆".repeat(5 - (int)avgRating);
            System.out.println("📊 " + stars + " (" + String.format("%.2f", avgRating) + ")");
        }
        System.out.println("✦────────────────────────────────✦");
    }

    // ⭐ دالة جديدة: Suggest Top 3 Products by Rating
    public void suggestTop3Products() {
        System.out.println("✦ Top 3 Products by Rating ✦");
        System.out.println("✦────────────────────────────────✦");

        if (allProducts == null) {
            System.out.println("❌ Products data not available");
            return;
        }

        // الحصول على كل المنتجات
        LinkedList<ProductNode> allProductsList = allProducts.get_Products();
        if (allProductsList.empty()) {
            System.out.println("📭 No products available");
            System.out.println("✦────────────────────────────────✦");
            return;
        }

        // تحويل القائمة لمصفوفة للترتيب
        int count = allProductsList.size();
        ProductNode[] productsArray = new ProductNode[count];
        
        allProductsList.findFirst();
        for (int i = 0; i < count; i++) {
            productsArray[i] = allProductsList.retrieve();
            if (!allProductsList.last()) allProductsList.findNext();
        }

        // ترتيب المنتجات حسب متوسط التقييم (تنازلي)
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (productsArray[j].getAverageRating() < productsArray[j + 1].getAverageRating()) {
                    ProductNode temp = productsArray[j];
                    productsArray[j] = productsArray[j + 1];
                    productsArray[j + 1] = temp;
                }
            }
        }

        // عرض أفضل 3 منتجات
        int displayCount = Math.min(3, count);
        for (int i = 0; i < displayCount; i++) {
            ProductNode product = productsArray[i];
            double avgRating = product.getAverageRating();
            String stars = "⭐".repeat((int)avgRating) + "☆".repeat(5 - (int)avgRating);
            
            System.out.println((i + 1) + ". " + product.getName() +
                             " (ID: " + product.getProductId() + ")" +
                             " - Rating: " + String.format("%.2f", avgRating) +
                             " " + stars);
        }

        if (displayCount == 0) {
            System.out.println("📭 No products with ratings available");
        }
        System.out.println("✦────────────────────────────────✦");
    }

    public void setAllCustomers(CustomerDataStructure cs) {
        this.allCustomers = cs;
    }

    public void setAllProducts(ProductDataStructure ps) {
        this.allProducts = ps;
    }
}