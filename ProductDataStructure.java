
package datastructer;



import static datastructer.CustomerDataStructure.ConvertStringToCustomer;
import java.io.File;
import java.util.Scanner;

public class ProductDataStructure {
    private LinkedList<ProductNode> products;    

    public ProductDataStructure(LinkedList<ProductNode> input_products) {
        products = input_products;
    }

    public ProductDataStructure() {
        products = new LinkedList<>();
    }

    public LinkedList<ProductNode> get_Products() {
        return products;
    }

    public ProductNode SearchProductByid(int id) {
        if (products.empty()) {
            return null;
        } else {
            products.findFirst();
            while (true) {
                if (products.retrieve().getProductId() == id) {
                    return products.retrieve();
                }
                if (products.last())
                    break;
                else
                    products.findNext();
            }
        }
        return null;
    }

    public void addProduct(ProductNode p) {
        if (SearchProductByid(p.getProductId()) == null) { 
            products.addLast(p);
            System.out.println("Product added: " + p.getName());
        } else {
            System.out.println("Product with ID " + p.getProductId() + " already exists!");
        }
    }

    public void removeProduct(int id) {
        if (SearchProductByid(id) != null) {
            products.remove();
            System.out.println("Product removed: " + id);
        } else
            System.out.println("Product ID not found!");
    }

    public void updateProduct(int id, ProductNode p) {
        ProductNode old = SearchProductByid(id);
        if (old == null)
            System.out.println("The product does not exist to make update!");
        else
            old.UpdateProduct(p);
    }

    public void displayOutOfStock() {      
        System.out.println("Out-of-stock products:"); 
        if (products.empty()) {
            System.out.println("There is no products existing!");        
        } else {
            boolean found = false;
            products.findFirst();
            while (true) {
                if (products.retrieve().getStock() == 0) {
                    products.retrieve().display();
                    found = true;
                }
                if (products.last())
                    break;
                else
                    products.findNext();
            }
            if (!found) System.out.println("All products in stock!");
        }
    }

    public void displayAllProducts() {
        System.out.println("✦ All Products ✦");
        if (products.empty()) {
            System.out.println("no products exist");
            return;
        } else {
            products.findFirst();
            while (true) {
                ProductNode p = products.retrieve();
                p.display(); 
                p.displayReviews();
                System.out.println("✦────────────────────────────────✦");
                if (products.last())
                    break;
                else
                    products.findNext();
            }
        }
    }



    public void assignProduct(ReviewNode r) {
        ProductNode p = SearchProductByid(r.getProductId());
        if (p == null)
            System.out.println("The product does tnot exist to assign review " + r.getReviewId() + " to it");
        else
            p.addReview(r);
    }

    public static ProductNode convert_String_to_product(String Line) {
        String a[] = Line.split(",");
        ProductNode p = new ProductNode(Integer.parseInt(a[0]), a[1],
                Double.parseDouble(a[2]), Integer.parseInt(a[3]));
        return p;
    }
     public void loadProducts(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);
            System.out.println("Reading file: " + fileName);
            System.out.println("✦────────────────────────────────✦");
            if (read.hasNextLine()) read.nextLine(); 

            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (line.isEmpty()) continue;  
                ProductNode p = convert_String_to_product(line);
                    products.addLast(p);
            }

            read.close();
            System.out.println("✦────────────────────────────────✦");
            System.out.println("Products loaded successfully \n");
        } catch (Exception e) {
            System.out.println("Error occur during loading the customers: " + e.getMessage());
        }
    }
    

}
