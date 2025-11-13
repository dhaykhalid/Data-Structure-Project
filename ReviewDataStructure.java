

package ph1;

import java.io.File;
import java.util.Scanner;

public class ReviewDataStructure {
private LinkedList<ReviewNode> reviews;
private ProductDataStructure allProducts;
private CustomerDataStructure allCustomers;

public ReviewDataStructure() {
reviews = new LinkedList<>();
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

reviews.findFirst();
while (true) {
ReviewNode p = reviews.retrieve();
p.display();
if (reviews.last()) break;
reviews.findNext();
}
}

public static ReviewNode convertStringToReview(String Line) {
String[] a = Line.split(",");
// تأكد من أن في 5 أجزاء
if (a.length < 5) {
System.out.println(" Error: Not enough parts in: " + Line);
return null;
}

ReviewNode r = new ReviewNode(
Integer.parseInt(a[0].trim()),
Integer.parseInt(a[1].trim()),
Integer.parseInt(a[2].trim()),
Integer.parseInt(a[3].trim()), // الريتنق
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

int loadedCount = 0;
while (read.hasNextLine()) {
String line = read.nextLine().trim();
if (line.isEmpty()) continue;
ReviewNode r = convertStringToReview(line);
addReview(r);
loadedCount++;
}

read.close();
System.out.println(" Reviews loaded: " + loadedCount);
System.out.println("✦────────────────────────────────✦");
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

public void getAverageRating(int productId) {
if (allProducts != null) {
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
String stars = "⭐".repeat((int)avgRating) + "☆".repeat(5 - (int)avgRating);
System.out.println(" " + stars + " (" + String.format("%.2f", avgRating) + ")");
}
System.out.println("✦────────────────────────────────✦");
} else {
System.out.println(" Products data not available!");
}
}

public void suggestTop3Products() {
System.out.println(" Top 3 Products by Rating ");
System.out.println("✦────────────────────────────────✦");

if (allProducts == null) {
System.out.println(" Products data not available");
return;
}

LinkedList<ProductNode> allProductsList = allProducts.get_Products();
if (allProductsList.empty()) {
System.out.println(" No products available");
return;
}

// إنشاء قائمة لتخزين كل المنتجات مع تقييماتها (حتى لو صفر)
LinkedList<ProductRating> productRatings = new LinkedList<>();

// جمع كل المنتجات مع تقييماتها
allProductsList.findFirst();
while (true) {
ProductNode product = allProductsList.retrieve();
double avgRating = product.getAverageRating();

// خذ كل المنتجات حتى لو rating = 0
productRatings.addLast(new ProductRating(product, avgRating));

if (allProductsList.last()) break;
allProductsList.findNext();
}

if (productRatings.empty()) {
System.out.println(" No products found");
System.out.println("✦────────────────────────────────✦");
return;
}

// ترتيب المنتجات تنازلياً حسب التقييم
if (!productRatings.empty()) {
int size = productRatings.size();

for (int i = 0; i < size - 1; i++) {
productRatings.findFirst();
for (int j = 0; j < size - i - 1; j++) {
ProductRating current = productRatings.retrieve();
productRatings.findNext();
ProductRating next = productRatings.retrieve();

if (current.rating < next.rating) {
// مبادلة
productRatings.update(next);
productRatings.findFirst();
for (int k = 0; k < j; k++) productRatings.findNext();
productRatings.update(current);
}

productRatings.findFirst();
for (int k = 0; k <= j; k++) productRatings.findNext();
}
}
}

// عرض أفضل 3 منتجات
System.out.println("Top 3 Products by Average Rating:");
productRatings.findFirst();

int displayed = 0;
for (int i = 0; i < 3 && i < productRatings.size(); i++) {
ProductRating top = productRatings.retrieve();
String stars = "⭐".repeat((int)top.rating) + "☆".repeat(5 - (int)top.rating);

System.out.println((i + 1) + ". " + top.product.getName() +
" (ID: " + top.product.getProductId() + ")" +
" - Rating: " + String.format("%.2f", top.rating) +
" " + stars);

displayed++;
if (productRatings.last()) break;
productRatings.findNext();
}

if (displayed == 0) {
System.out.println(" All products have zero ratings");
}

System.out.println("✦────────────────────────────────✦");
}

public void setAllCustomers(CustomerDataStructure cs) {
this.allCustomers = cs;
}

public void setAllProducts(ProductDataStructure ps) {
this.allProducts = ps;
}

// كلاس مساعد داخل ReviewDataStructure
class ProductRating {
ProductNode product;
double rating;

public ProductRating(ProductNode product, double rating) {
this.product = product;
this.rating = rating;
}
}
}
