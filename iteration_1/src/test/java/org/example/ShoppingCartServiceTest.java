package org.example;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartServiceTest {

    private ShoppingCartService subject;

    @Before
    public void setup() {
        System.out.println("new");
        subject = new ShoppingCartService();
    }


    @Test
    public void add_must_add_item() {
        subject.add("TestArticle", BigDecimal.TEN, 2);

        Item found = null;
        for (Item item : subject.getItems()) {
            if ( "TestArticle".equals(item.getArticle()) ) {
                found = item;
            }
        }
        assertNotNull("Shopping cart must contains \"TestArticle\"", found);
    }

    @Test
    public void add_must_increase_quantity() {
        subject.add("TestArticle", BigDecimal.TEN, 1);
        subject.add("TestArticle", BigDecimal.TEN, 1);

        Item found = null;
        for (Item item : subject.getItems()) {
            if ( "TestArticle".equals(item.getArticle()) ) {
                found = item;
            }
        }
        assertNotNull("Shopping cart must contains 2 \"TestArticle\" but has null", found);
        assertEquals("Shopping cart must contains 2 \"TestArticle\"", 2, found.getQuantity());
    }

    @Test
    public void getTotal_must_return_total_for_all_items() {
        subject.add("10 * 2", BigDecimal.TEN, 2);
        subject.add("5", BigDecimal.valueOf(5), 1);

        assertEquals(BigDecimal.valueOf((10 * 2)+5), subject.getTotal());
    }
}
