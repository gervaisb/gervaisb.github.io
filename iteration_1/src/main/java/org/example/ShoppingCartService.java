package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartService {

    private final List<Item> items = new ArrayList<>();

    public void add(String article, BigDecimal price, int quantity) {
        boolean found = false;
        for (Item item : items) {
            if ( article.equals(item.getArticle()) ) {
                item.setQuantity(item.getQuantity()+quantity);
                found = true;
            }
        }
        if ( !found ) {
            items.add(new Item(article, price, quantity));
        }
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : items) {
            BigDecimal unitPrice = item.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            total = total.add(unitPrice.multiply(quantity));
        }
        return total;
    }

    public List<Item> getItems() {
        return items;
    }
}

