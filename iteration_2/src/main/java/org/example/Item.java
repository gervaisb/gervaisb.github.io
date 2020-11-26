package org.example;

import org.example.domain.ArticleId;
import org.example.domain.Price;
import org.example.domain.Quantity;

class Item {
    private ArticleId articleId;
    private Price unitPrice;
    private Quantity quantity;

    public Item(ArticleId articleId, Price unitPrice, Quantity quantity) {
        this.articleId = articleId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public ArticleId getArticleId() {
        return articleId;
    }

    public void setArticleId(ArticleId articleId) {
        this.articleId = articleId;
    }

    public Price getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Price unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
