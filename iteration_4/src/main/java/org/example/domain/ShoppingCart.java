package org.example.domain;

import java.util.*;

public class ShoppingCart {

    public static class CartItem {
        private final ArticleId articleId;
        private final Price unitPrice;
        private final Quantity quantity;

        CartItem(ArticleId articleId, Price unitPrice, Quantity quantity) {
            this.articleId = articleId;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public ArticleId getArticleId() {
            return articleId;
        }

        public Price getSubTotal() {
            return unitPrice.multiply(quantity);
        }

        public Quantity getQuantity() {
            return quantity;
        }

        CartItem add(Quantity more) {
            return new CartItem(articleId, unitPrice, this.quantity.plus(more));
        }
    }


    private final Map<ArticleId, CartItem> itemsByArticleId = new HashMap<>();
    private final ClientId clientId;

    public ShoppingCart(ClientId clientId, Collection<CartItem> cartItems) {
        this.clientId = clientId;
        cartItems.forEach(cartItem -> {
            this.itemsByArticleId.put(cartItem.articleId, cartItem);
        });
    }

    public void add(ArticleId articleId, Price price, Quantity quantity) {
        if ( itemsByArticleId.containsKey(articleId) ) {
            itemsByArticleId.computeIfPresent(articleId, (a, item) -> item.add(quantity));
        } else {
            itemsByArticleId.put(articleId, new CartItem(articleId, price, quantity));
        }
    }

    public Price getTotal() {
        return itemsByArticleId.values().stream()
                .map(CartItem::getSubTotal)
                .reduce(Price.euros(0), Price::plus);
    }

    public List<CartItem> getItemsByArticleId() {
        return Collections.unmodifiableList(new ArrayList<>(itemsByArticleId.values()));
    }
}

