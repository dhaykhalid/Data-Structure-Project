package datastructer;



import java.io.File;
import java.util.Scanner;

public class ProductDataStructure {

    private BSTMap<Integer, ProductNode> products;

    public ProductDataStructure(BSTMap<Integer, ProductNode> input_products) {
        products = input_products;
    }

    public ProductDataStructure() {
        products = new BSTMap<>();
    }

    public BSTMap<Integer, ProductNode> get_Products() {
        return products;
    }

    // ===============================================================
    // Search Product By ID – using BSTMap
    // ===============================================================

    public ProductNode SearchProductByid(int id) {
        if (products.empty())
            return null;

        boolean found = products.find(id);
        if (found)
            return products.retrieve();

        return null;
    }

    // ===============================================================
    // Add Product – BST insert
    // ===============================================================

    public void addProduct(ProductNode p) {
        boolean inserted = products.insert(p.getProductId(), p);

        if (inserted)
            System.out.println("Product added: " + p.getName());
        else
            System.out.println("Product with ID " + p.getProductId() + " already exists!");
    }

    // ===============================================================
    // Remove Product – BST remove
    // ===============================================================

    public void removeProduct(int id) {
        boolean removed = products.remove(id);

        if (removed)
            System.out.println("Product removed: " + id);
        else
            System.out.println("Product ID not found!");
    }

    // ===============================================================
    // Update Product
    // ===============================================================

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
            return;
        }
        inOrder_out_of_stock(products.getRoot());
    }

    private void inOrder_out_of_stock(BSTnode<Integer, ProductNode> p) {
        if (p == null)
            return;

        inOrder_out_of_stock(p.left);

        if (p.data.getStock() == 0) {
            System.out.println("key=" + p.key);
            p.data.display();
        }

        inOrder_out_of_stock(p.right);
    }


    public void displayAllProducts() {
        System.out.println("✦ All Products ✦");

        if (products.empty()) {
            System.out.println("no products exist");
            return;
        }

        inOrder_all(products.getRoot());
    }

    private void inOrder_all(BSTnode<Integer, ProductNode> p) {
        if (p == null)
            return;

        inOrder_all(p.left);

        ProductNode pr = p.data;
        pr.display();
        pr.displayReviews();
        System.out.println("✦────────────────────────────────✦");

        inOrder_all(p.right);
    }


    public void assignProduct(ReviewNode r) {
        ProductNode p = SearchProductByid(r.getProductId());

        if (p == null)
            System.out.println("The product does tnot exist to assign review " + r.getReviewId() + " to it");
        else
            p.addReview(r);
    }



    public void displayTop3Products() {
        if (products.empty()) {
            System.out.println("No products available!");
            return;
        }

        int count = countNodes(products.getRoot());
        ProductNode[] all = new ProductNode[count];
        fillProducts(products.getRoot(), all, new int[]{0});

        // sort by rating
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (all[j].getAverageRating() < all[j + 1].getAverageRating()) {
                    ProductNode tmp = all[j];
                    all[j] = all[j + 1];
                    all[j + 1] = tmp;
                }
            }
        }

        System.out.println("✦Top 3 Products by Rating ✦");
        for (int i = 0; i < Math.min(3, count); i++) {
            System.out.println(all[i].getName() + " has an average rating of " + all[i].getAverageRating());
        }
    }

    private int countNodes(BSTnode<Integer, ProductNode> p) {
        if (p == null) return 0;
        return 1 + countNodes(p.left) + countNodes(p.right);
    }

    private void fillProducts(BSTnode<Integer, ProductNode> p, ProductNode[] arr, int[] index) {
        if (p == null) return;
        fillProducts(p.left, arr, index);
        arr[index[0]++] = p.data;
        fillProducts(p.right, arr, index);
    }

  
    public static ProductNode convert_String_to_product(String Line) {
        String a[] = Line.split(",");

        ProductNode p = new ProductNode(
                Integer.parseInt(a[0]),
                a[1],
                Double.parseDouble(a[2]),
                Integer.parseInt(a[3])
        );
        return p;
    }

    

    public void loadProducts(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);
            System.out.println("Reading file: " + fileName);
            System.out.println("✦────────────────────────────────✦");

            if (read.hasNextLine())
                read.nextLine(); // skip header

            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (line.isEmpty())
                    continue;

                ProductNode p = convert_String_to_product(line);
                products.insert(p.getProductId(), p);
            }

            read.close();
            System.out.println("✦────────────────────────────────✦");
            System.out.println("Products loaded successfully \n");

        } catch (Exception e) {
            System.out.println("Error occur during loading the customers: " + e.getMessage());
        }
    }

    BSTMap<Integer, ProductNode> getBST() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
