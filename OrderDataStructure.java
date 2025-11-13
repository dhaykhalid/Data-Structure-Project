package ph1;



	import java.io.File;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.util.Scanner;

	public class OrderDataStructure {
	    private LinkedList<OrderNode> allOrders;
	    private CustomerDataStructure allCustomers;
	    static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    public OrderDataStructure() {
	        allCustomers = new CustomerDataStructure();
	        allOrders = new LinkedList<>();
	    }

	    public LinkedList<OrderNode> getOrders() {
	        return allOrders;
	    }

	    public OrderNode searchOrderById(int id) {
	        if (allOrders.empty()) return null;

	        allOrders.findFirst();
	        while (true) {
	            OrderNode o = allOrders.retrieve();
	            if (o.getOrderID() == id) return o;
	            if (allOrders.last()) break;
	            allOrders.findNext();
	        }
	        return null;
	    }

	    public void UpdateOrderState(int id, String newStatus) {
	        OrderNode order = searchOrderById(id);
	        if (order != null) {
	            order.setStatus(newStatus);
	            System.out.println(" Order " + id + " status updated to: " + newStatus);
	        } else {
	            System.out.println(" Order ID not found");
	        }
	    }

	    public void removeOrder(int id) {
	        if (searchOrderById(id) != null) {
	            allOrders.remove();
	            System.out.println(" Order removed: " + id);
	        } else {
	            System.out.println(" Order ID not found");
	        }
	    }

	    public void addOrder(OrderNode ord) {
	        if (searchOrderById(ord.getOrderID()) == null) {
	            allOrders.insert(ord);
	            // ربط الطلب بالعميل
	            CustomerNode customer = allCustomers.searchById(ord.getCustomerID());
	            if (customer != null) {
	                customer.addOrder(ord);
	                System.out.println(" Order " + ord.getOrderID() + " linked to " + customer.getName());
	            }
	        }
	    }

	    public static OrderNode convertStringToProduct(String Line) {
	        String a[] = Line.split(",");
	        int orderId = Integer.parseInt(a[0].trim().replace("\"", ""));
	        int customerId = Integer.parseInt(a[1].trim().replace("\"", ""));
	        String productIds = a[2].trim().replace("\"", "");
	        double totalPrice = Double.parseDouble(a[3]);
	        LocalDate date = LocalDate.parse(a[4], df);
	        String status = a[5].trim();

	        return new OrderNode(orderId, customerId, productIds, totalPrice, date, status);
	    }

	    public void loadOrders(String fileName) {
	        try {
	            File f = new File(fileName);
	            Scanner read = new Scanner(f);
	            System.out.println(" Reading orders from: " + fileName);
	            
	            if (read.hasNextLine()) read.nextLine();

	            int loadedCount = 0;
	            while (read.hasNextLine()) {
	                String line = read.nextLine().trim();
	                if (!line.isEmpty()) {
	                    OrderNode ord = convertStringToProduct(line);
	                    addOrder(ord);
	                    loadedCount++;
	                }
	            }

	            read.close();
	            System.out.println(" Orders loaded: " + loadedCount);
	        } catch (Exception e) {
	            System.out.println(" Error loading orders: " + e.getMessage());
	        }
	    }

	    public void displayAllOrders() {
	        if (allOrders.empty()) {
	            System.out.println("📭 No orders found!");
	            return;
	        }

	        System.out.println("✦ All Orders ✦");
	        System.out.println("✦────────────────────────────────✦");

	        allOrders.findFirst();
	        while (true) {
	            OrderNode o = allOrders.retrieve();
	            o.display();
	            if (allOrders.last()) break;
	            allOrders.findNext();
	        }
	    }

	    public void displayAllOrders_between2dates(LocalDate d1, LocalDate d2) {
	        if (allOrders.empty()) {
	            System.out.println("📭 No orders found!");
	            return;
	        }

	        System.out.println("✦ Orders between " + d1 + " and " + d2 + " ✦");
	        System.out.println("✦────────────────────────────────✦");

	        boolean found = false;
	        allOrders.findFirst();
	        while (true) {
	            OrderNode o = allOrders.retrieve();
	            if (o.getOrderDate().compareTo(d1) >= 0 && o.getOrderDate().compareTo(d2) <= 0) {
	                o.display();
	                found = true;
	            }
	            if (allOrders.last()) break;
	            allOrders.findNext();
	        }

	        if (!found) {
	            System.out.println("📭 No orders found in this date range");
	        }
	        System.out.println("✦────────────────────────────────✦");
	    }

	    public void setAllCustomers(CustomerDataStructure cs) {
	        this.allCustomers = cs;
	    }
	}

