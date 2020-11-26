package org.example;

import org.example.domain.ArticleId;
import org.example.domain.Price;
import org.example.domain.Quantity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartServiceTest {

    private final Price tenEuros = Price.euros(10);
    private final Quantity two = Quantity.of(2);
    private final Quantity one = Quantity.of(1);
    private ShoppingCartService subject;

    @Before
    public void setup() {
        subject = new ShoppingCartService();
    }


    @Test
    public void add_must_add_item() {
        ArticleId articleId = ArticleId.next();

        subject.add(articleId, tenEuros, two);

        CartItem found = null;
        for (CartItem item : subject.getItems()) {
            if ( articleId.equals(item.getArticleId()) ) {
                found = item;
            }
        }
        assertNotNull("Shopping cart must contains item "+articleId, found);
    }

    @Test
    public void add_must_increase_quantity() {
        ArticleId articleId = ArticleId.next();
        subject.add(articleId, tenEuros, one);
        subject.add(articleId, tenEuros, one);

        CartItem found = null;
        for (CartItem item : subject.getItems()) {
            if ( articleId.equals(item.getArticleId()) ) {
                found = item;
            }
        }
        assertNotNull("Shopping cart must contains 2 "+articleId+" but has null", found);
        assertEquals("Shopping cart must contains 2 "+articleId, two, found.getQuantity());
    }

    @Test
    public void getTotal_must_return_total_for_all_items() {
        subject.add(ArticleId.next(), Price.euros(10), two);
        subject.add(ArticleId.next(), Price.euros(5), one);

        assertEquals(Price.euros( (10 * 2) + 5 ), subject.getTotal());
    }
}
