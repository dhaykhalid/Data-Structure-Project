package csc112;

public class CustomerNode {
    private int customerID;
    private String name;
    private String email;
    private LinkedList<OrderNode> orders;

    public CustomerNode(int customerID, String name, String email) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.orders = new LinkedList<>();
    }

    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void addOrder(OrderNode order){
        orders.addLast(order);
    }

    public void display(){
        System.out.println("CustomerID: " + customerID);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("✦────────────────────────────────✦");
    }

    public void printOrderHistory() {
        System.out.println("✦ Order History for " + name + " ✦");
        System.out.println("✦────────────────────────────────✦");

        if (orders.empty()) {
            System.out.println("📭 No orders found");
            System.out.println("✦────────────────────────────────✦");
            return;
        }

        orders.findFirst();
        while (true) {
            OrderNode order = orders.retrieve();
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Products: " + order.getProdIds());
            System.out.println("Total: $" + order.getTotalPrice());
            System.out.println("Date: " + order.getOrderDate());
            System.out.println("Status: " + order.getStatus());
            System.out.println("────────────────────");
            
            if (orders.last()) break;
            orders.findNext();
        }
        System.out.println("✦────────────────────────────────✦");
    }

    // دالة addReview فارغة علشان ما يطلع error
    public void addReview(ReviewNode review) {
        // ما نحتاج نضيف مراجعات للعميل حالياً
    }
}