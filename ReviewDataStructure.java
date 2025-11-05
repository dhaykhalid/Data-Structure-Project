
package datastructer;


import static datastructer.ProductDataStructure.convert_String_to_product;
import java.io.File;
import java.util.Scanner;

public class ReviewDataStructure {
    private static LinkedList<ReviewNode> reviews;    
    private ProductDataStructure all_products;
    private CustomerDataStructure all_Customers;

    public ReviewDataStructure(LinkedList<ReviewNode> reviews,
                               LinkedList<ProductNode> input_products,
                               LinkedList<CustomerNode> input_customers) {
        this.reviews = reviews;
        all_products = new ProductDataStructure(input_products);
        all_Customers = new CustomerDataStructure(input_customers);
    }

    public ReviewDataStructure() {
        reviews = new LinkedList<>();
        all_products = new ProductDataStructure();
        all_Customers = new CustomerDataStructure();
    }

    public LinkedList<ReviewNode> get_all_Reviews() {
        return reviews;
    }

    public ProductDataStructure get_all_Products() {
        return all_products;
    }

    public ReviewNode SearchReviewByid(int id) {
        if (reviews.empty()) {
            return null;
        } else {
            reviews.findFirst();
            while (true) {
                if (reviews.retrieve().getReviewId() == id) {
                    return reviews.retrieve();
                }
                if (reviews.last())
                    break;
                else
                    reviews.findNext();
            }
        }
        return null;
    }

    public void assign_to_product(ReviewNode r) {
        ProductNode p = all_products.SearchProductByid(r.getProductId());
        if (p != null)
            p.addReview(r);
    }

    public void assign_to_customer(ReviewNode r) {
        CustomerNode p = all_Customers.searchById(r.getCustomerId());
        if (p != null)
            p.addReview(r);
    }

    public void addReview(ReviewNode r) {
        if (SearchReviewByid(r.getReviewId()) == null) { 
            reviews.addLast(r);
            assign_to_product(r);
            assign_to_customer(r);
        } else {
            System.out.println("Review with ID " + r.getReviewId() + " already exists!");
        }
    }
    
  

    public void updateReview(int id, ReviewNode p) {
        ReviewNode old = SearchReviewByid(id);
        if (old == null)
            System.out.println("Review does not exist to make an update!");
        else
            old.UpdateReview(p);
    }

    public void displayAllReviews() {
        System.out.println("✦ All Reviews ✦");
        if (reviews.empty()) {
            System.out.println("no reviews exist");
            return;
        } else {
            reviews.findFirst();
            while (true) {
                ReviewNode p = reviews.retrieve();
                p.display();
                System.out.println("✦────────────────────────────────✦");
                if (reviews.last())
                    break;
                else
                    reviews.findNext();
            }
        }
    }

    public static ReviewNode convert_String_to_Review(String Line) {
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
            System.out.println("Reading file: " + fileName);
            System.out.println("✦────────────────────────────────✦");
            if (read.hasNextLine()) read.nextLine(); 

            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (line.isEmpty()) continue;  
               ReviewNode r = convert_String_to_Review(line);
                    addReview(r);
            }

            read.close();
            System.out.println("✦────────────────────────────────✦");
            System.out.println("Review loaded successfully \n");
        } catch (Exception e) {
            System.out.println("Error occur during loading the customers: " + e.getMessage());
        }
    }
    
}
