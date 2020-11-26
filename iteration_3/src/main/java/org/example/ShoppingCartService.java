package org.example;

import org.example.domain.ArticleId;
import org.example.domain.Price;
import org.example.domain.Quantity;

import java.util.*;

public class ShoppingCartService {



    private Map<ArticleId, CartItem> items = new HashMap<>();

    public void add(ArticleId articleId, Price price, Quantity quantity) {
        if ( items.containsKey(articleId) ) {
            items.computeIfPresent(articleId, (a, item) -> {
                item.add(quantity);

                return item;
            });
        } else {
            items.put(articleId, new CartItem(articleId, price, quantity));
        }
    }

    public Price getTotal() {
        return items.values().stream()
                .map(CartItem::getSubTotal)
                .reduce(Price.euros(0), Price::plus);
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }
}

