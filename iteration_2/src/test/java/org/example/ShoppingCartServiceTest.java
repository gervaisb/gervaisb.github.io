package org.example;

import org.example.domain.ArticleId;
import org.example.domain.Price;
import org.example.domain.Quantity;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartServiceTest {

    private final Price tenEuros = new Price(BigDecimal.TEN, Currency.getInstance("EUR"));
    private final Quantity two = new Quantity(2);
    private final Quantity one = new Quantity(1);
    private ShoppingCartService subject;

    @Before
    public void setup() {
        subject = new ShoppingCartService();
    }


    @Test
    public void add_must_add_item() {
        ArticleId articleId = new ArticleId(UUID.randomUUID());

        subject.add(articleId, tenEuros, two);

        Item found = null;
        for (Item item : subject.getItems()) {
            if ( articleId.equals(item.getArticleId()) ) {
                found = item;
            }
        }
        assertNotNull("Shopping cart must contains item "+articleId, found);
    }

    @Test
    public void add_must_increase_quantity() {
        ArticleId articleId = new ArticleId(UUID.randomUUID());
        subject.add(articleId, tenEuros, one);
        subject.add(articleId, tenEuros, one);

        Item found = null;
        for (Item item : subject.getItems()) {
            if ( articleId.equals(item.getArticleId()) ) {
                found = item;
            }
        }
        assertNotNull("Shopping cart must contains 2 "+articleId+" but has null", found);
        assertEquals("Shopping cart must contains 2 "+articleId, two, found.getQuantity());
    }

    @Test
    public void getTotal_must_return_total_for_all_items() {
        subject.add(new ArticleId(UUID.randomUUID()), new Price(BigDecimal.valueOf(10), Currency.getInstance("EUR")), two);
        subject.add(new ArticleId(UUID.randomUUID()), new Price(BigDecimal.valueOf(5), Currency.getInstance("EUR")), one);

        assertEquals(new Price(BigDecimal.valueOf( (10 * 2) + 5), Currency.getInstance("EUR")), subject.getTotal());
    }
}
