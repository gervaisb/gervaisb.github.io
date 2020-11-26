package org.example;

import org.example.domain.ArticleId;
import org.example.domain.Price;
import org.example.domain.Quantity;

class CartItem {
    private ArticleId articleId;
    private Price unitPrice;
    private Quantity quantity;

    public CartItem(ArticleId articleId, Price unitPrice, Quantity quantity) {
        this.articleId = articleId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public void add(Quantity more) {
        this.quantity = this.quantity.plus(more);
    }

    public ArticleId getArticleId() {
        return articleId;
    }

    public void setArticleId(ArticleId articleId) {
        this.articleId = articleId;
    }

    public Price getSubTotal() {
        return unitPrice.multiply(quantity);
    }

    public Quantity getQuantity() {
        return quantity;
    }

}
