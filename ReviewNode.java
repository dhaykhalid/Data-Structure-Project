package ph1;

public class ReviewNode {
    private int reviewId;
    private int productId;
    // نخزن الـ Customer ID كـ String (عادي)
    private String reviewerName;
    private int rating;
    private String comment;
    private ReviewNode next;
    
    // الكونستركتر الأساسي (يستقبل اسم/معرّف المراجع كـ String)
    public ReviewNode(int reviewId, int productId, String reviewerName, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;
        this.next = null;
    }

    // كونستركتر إضافي يستقبل Customer ID كـ int
    // عشان يناسب استدعاءات:
    // new ReviewNode(rid, pid, cid, rating, comment)
    public ReviewNode(int reviewId, int productId, int reviewerId, int rating, String comment) {
        this(reviewId, productId, String.valueOf(reviewerId), rating, comment);
    }
    
    // Getters
    public int getReviewId() { return reviewId; }
    public int getProductId() { return productId; }
    public String getReviewerName() { return reviewerName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public ReviewNode getNext() { return next; }

    // Setters
    public void setNext(ReviewNode next) { this.next = next; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }

    // يستخدمها ReviewDataStructure.updateReview
    public void UpdateReview(ReviewNode other) {
        this.reviewId = other.reviewId;
        this.productId = other.productId;
        this.reviewerName = other.reviewerName;
        this.rating = other.rating;
        this.comment = other.comment;
    }
    
    // Display method
    public void display() {
        System.out.println("Review ID: " + reviewId);
        System.out.println("Product ID: " + productId);
        System.out.println("Reviewer: " + reviewerName);
        System.out.println("Rating: " + rating + "/5");
        System.out.println("Comment: " + comment);
        System.out.println("-------------------");
    }
    
    // toString method
    @Override
    public String toString() {
        return String.format(
            "Review[ID: %d, Product: %d, Reviewer: %s, Rating: %d/5, Comment: %s]", 
            reviewId, productId, reviewerName, rating, comment
        );
    }
}

