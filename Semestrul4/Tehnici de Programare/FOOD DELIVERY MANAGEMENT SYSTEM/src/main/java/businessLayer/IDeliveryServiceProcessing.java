package businessLayer;
import model.LoggingAccount;
import java.util.List;
import java.util.Map;

public interface IDeliveryServiceProcessing {
    /**
     * Citeste din fiserul products.csv si face o serializare
     * @post wellFormed()
     */
    void readDataFromFile();

    /**
     * Returneaza lista produselor din meniu
     * @return lista produselor din meniu
     */
    List<BaseProduct> getProducts();

    /**
     * Actualizeaza lista produselor din meniu
     * @prea a != null
     * @param a noua lista cu produsele din meniu
     * @post wellFormed()
     */
    void setProducts(List<BaseProduct> a);

    /**
     * Aceasta metoda adauga un client in lista de clienti
     * @pre a!=null
     * @param a
     * @post wellFormed()
     */
    void addAccount(LoggingAccount a);

    /**
     * Aceasta functie sterge un produs din lista de produse
     * @pre b != null && this.products !  = null
     * @param b
     * @post wellFormed()
     */
    void deleteProduct(BaseProduct b);

    /**
     *Aceasta functie salveaza o comanda cu lista de produse comandate
     * @pre  order != null && menu != null
     * @param order
     * @param menu
     *  @post wellFormed()
     */
    void addOrderListMap(Order order, List<BaseProduct> menu);

    /**
     *Aceasta metoda cauta un client in lista de clienti
     * @param a
     * @return gasit
     */
    boolean findAccount(LoggingAccount a);

    /**
     *Aceasta metoda adauga un produs in lista cu produse
     * @param b
     * @post wellFormed()
     */
    void addProdus(BaseProduct b);

    /**
     *Aceasta functie ne da accesul la lista cu comenzi
     * @return orders
     */
    List<Order> getOrders();

    /**
     * Aceasta functie adauga o comanda in lista de comenzi
     * @param o
     * @post wellFormed()
     *
     */
    void addOrder(Order o);

    /**
     *Aceasta functie porneste observatorul de pe frameul de employee
     * @param o
     */
    void addObservator(Order o);

    /**
     *Aceasta functie returneaza lista de clienti
     * @return accounts
     */
    List<LoggingAccount> getAccounts();

    /**
     *Aceasta functie este foloseta pentru post conditii
     * @return boolean value
     */
    boolean wellFormed();

    /**
     * Aceasta functie ne da accesul la maparea unei comenzi in memeorie
     * @return orderListMap
     */

    Map<Order, List<BaseProduct>> getOrderListMap();


}
