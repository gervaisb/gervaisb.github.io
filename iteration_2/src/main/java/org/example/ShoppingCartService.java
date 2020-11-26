package org.example;

import org.example.domain.ArticleId;
import org.example.domain.Price;
import org.example.domain.Quantity;

import java.math.BigDecimal;
import java.util.*;

public class ShoppingCartService {



    private Map<ArticleId, Item> items = new HashMap<>();

    public void add(ArticleId articleId, Price price, Quantity quantity) {
        if ( items.containsKey(articleId) ) {
            items.computeIfPresent(articleId, (a, item) -> {
                item.setQuantity(new Quantity(item.getQuantity().getValue()+quantity.getValue()));
                return item;
            });
        } else {
            items.put(articleId, new Item(articleId, price, quantity));
        }
    }

    public Price getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : items.values()) {
            Price unitPrice = item.getUnitPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity().getValue());
            total = total.add(unitPrice.getAmount().multiply(quantity));
        }
        return new Price(total, Currency.getInstance("EUR"));
    }

    public List<Item> getItems() {
        return new ArrayList<>(items.values());
    }
}

