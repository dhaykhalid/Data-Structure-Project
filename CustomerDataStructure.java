package ph1;



import java.io.File;
import java.util.Scanner;

public class CustomerDataStructure {
    private LinkedList<CustomerNode> customers;
   
    public CustomerDataStructure() {
        customers = new LinkedList<>();       
    }

    CustomerDataStructure(LinkedList<CustomerNode> input_customers) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public CustomerNode searchById(int id) {
        if (customers.empty()) return null;
        
        customers.findFirst();
        while (true) {
            CustomerNode customer = customers.retrieve();
            if (customer.getCustomerID() == id) return customer;
            if (customers.last()) break;
            customers.findNext();
        }
        return null;
    }

    public void addCustomer(CustomerNode c) {
        if (searchById(c.getCustomerID()) == null) {
            customers.insert(c);
            System.out.println(" Customer added: " + c.getName());
        } else {
            System.out.println(" Customer ID already exists");
        }
    }

    public void displayAll() {
        if (customers.empty()) {
            System.out.println(" No customers found");
            return;
        }
        System.out.println("✦ All Customers ✦");
        customers.findFirst();
        while (true) {
            customers.retrieve().display();
            if (customers.last()) break;
            customers.findNext();
        }
    }

    public void loadCustomers(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);
            System.out.println("Reading customers from: " + fileName);
            
            if (read.hasNextLine()) read.nextLine();

            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (!line.isEmpty()) {
                    CustomerNode c = ConvertStringToCustomer(line);
                    customers.insert(c);
                }
            }

            read.close();
            System.out.println(" Customers loaded successfully");
        } catch (Exception e) {
            System.out.println(" Error loading customers: " + e.getMessage());
        }
    }

    public static CustomerNode ConvertStringToCustomer(String Line) {
        String a[] = Line.split(",");
        return new CustomerNode(
            Integer.parseInt(a[0].trim()), 
            a[1].trim(), 
            a[2].trim()
        );
    }

    public void printOrderHistory(int id) {
        CustomerNode c = searchById(id);
        if (c == null) {
            System.out.println(" Customer not found");
        } else {
            c.printOrderHistory();
        }
    }
    
    public LinkedList<CustomerNode> getCustomers() {
        return customers;
    }
}
