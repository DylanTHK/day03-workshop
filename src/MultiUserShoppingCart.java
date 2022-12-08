// Main file to begin execution

public class MultiUserShoppingCart {
    
    public static void main(String[] args) {
        // System.out.println("Welcome to your shopping cart.");
        ShoppingCartDB cart = new ShoppingCartDB("cartdb"); // default folder

        cart.startShell();
    }
}