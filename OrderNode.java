package ph1;




/**
 *
 * @author Dalia
 */
import java.time.LocalDate;
public class OrderNode {
    private int orderID;
    private int customerID;
    private String prodIds; 
    private double totalPrice;
    private LocalDate orderDate;    
    private String status;
    private LinkedList<Integer>productIds;

    public OrderNode(int orderID, int customerID, String prodIds, double totalPrice,  LocalDate orderDate, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.prodIds=prodIds;       
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
      
         this.productIds = new LinkedList<>();
         addIds(prodIds);
    
    }
    public void addIds(String Ids)
    {
        String a[]=Ids.split(";");
        for(int i=0;i<a.length;i++)
          productIds.addLast(Integer.parseInt(a[i].trim()));
        
       
    } 
    public void addId(int id)
    {
    productIds.addLast(id);
    }
    public void UpdateOrder (OrderNode ord) {
        this.orderID = ord.orderID;
        this.customerID =  ord.customerID;
        this.prodIds =  ord.prodIds;
        this.totalPrice =  ord.totalPrice;
        this.orderDate =  ord.orderDate;
        this.status =  ord.status;
        this.productIds =  ord.productIds;
    }
 
    public void setStatus(String status) { 
    	
    	this.status = status; 
    }


    public void display() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer ID: " + customerID);
        System.out.print("Product IDs: " );
        productIds.display();
        System.out.println(""); // لا تحذفونه هذا عشان ماينحاس شكل الطباعة
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("✦────────────────────────────────✦");
    }
    
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getProdIds() {
		return prodIds;
	}
	public void setProdIds(String prodIds) {
		this.prodIds = prodIds;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public LinkedList<Integer> getProductIds() {
		return productIds;
	}
	public void setProductIds(LinkedList<Integer> productIds) {
		this.productIds = productIds;
	}
	public String getStatus() {
		return status;
	}

    String getProductIDs() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
      
}

