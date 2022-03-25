package businessLayer;

import dataLayer.FileWriter;
import dataLayer.Serializator;
import model.LoggingAccount;
import model.MenuItem;
import presentationLayer.EmployeeFrame;

import java.util.*;


public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    private Serializator ser;
    private Observer observator;
    private List<BaseProduct> products;
    private List <Order> orders;
    private List<LoggingAccount> accounts;
    private Map<Order,List<BaseProduct>> orderListMap;


    public DeliveryService(){
        accounts = new ArrayList<>();
        orderListMap = new HashMap<>();
        ser = new Serializator();
        products = new ArrayList<>();
        orders = new ArrayList<>();
        observator = new EmployeeFrame();
            accounts = (ArrayList<LoggingAccount>) ser.deserialize("accounts.txt");
            orderListMap = (Map<Order,List<BaseProduct>>) ser.deserialize("orderListMap.txt");
            orders = (ArrayList<Order>) ser.deserialize("orders.txt");
            products = (ArrayList<BaseProduct>) ser.deserialize("products.txt");


    }

    public void readDataFromFile(){
        FileWriter f = new FileWriter();
        HashSet<BaseProduct> products =f.readDataFromFile();
        List<BaseProduct> p= new ArrayList<>();
        for(BaseProduct bp : products){
            p.add(bp);
        }
        p.sort(new Comparator<BaseProduct>() {
            @Override
            public int compare(BaseProduct a, BaseProduct b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        this.products=p;
        ser.serialize("products.txt",p);
        assert wellFormed();
    }

    public List<BaseProduct> getProducts() {
        return products;
    }

    public void setProducts(List<BaseProduct> a){
        assert a != null;
        this.products=a;
        ser.serialize("products.txt",a);
        assert wellFormed();
    }


    public void addAccount(LoggingAccount a) {
        assert a!=null;
        accounts.add(a);
        ser.serialize("accounts.txt", accounts);
        assert wellFormed();
    }

    public void deleteProduct(BaseProduct b){
        assert b != null && this.products != null;
        this.products.remove(b);
        ser.serialize("products.txt",products);
        assert wellFormed();
    }

    public void addOrderListMap(Order order, List<BaseProduct> menu){
        assert order != null && menu != null;
        orderListMap.put(order,menu);
        ser.serialize("orderListMap.txt",orderListMap);
        assert wellFormed();

    }

    public boolean findAccount(LoggingAccount a){
        assert a != null && accounts != null;
        String user = a.getUserName();
        String pass = a.getPassword();
        boolean gasit = false;
        long num=0;

        num = accounts
                .stream()
                .filter(e -> e.getUserName().equals(user))
                .filter(e -> e.getPassword().equals(pass))
                . count();
       if(num!=0){
           gasit = true;
       }
        return gasit;
    }


    public void addProdus(BaseProduct b) {
        this.products.add(b);
        ser.serialize("products.txt",products);
        assert wellFormed();
    }


    public List<Order> getOrders() {
        return orders;
    }
    public void addOrder(Order o){
        orders.add(o);
        ser.serialize("orders.txt",orders);
        assert wellFormed();
    }

    public void addObservator(Order o) {
        observator.update(this, o);
    }

    public List<LoggingAccount> getAccounts() {
        return accounts;
    }
    public boolean wellFormed(){
        return orders == null || orderListMap == null || accounts == null || products == null || true;
    }

    public Map<Order, List<BaseProduct>> getOrderListMap() {
        return orderListMap;
    }
}
