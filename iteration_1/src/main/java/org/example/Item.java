package org.example;

import java.math.BigDecimal;
import java.util.Currency;

class Item {
    private String article;
    private BigDecimal price;
    private int quantity;

    public Item(String article, BigDecimal price, int quantity) {
        this.article = article;
        this.price = price;
        this.quantity = quantity;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
