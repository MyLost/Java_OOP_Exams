package gifts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class GiftFactoryTests {

    private Gift giftOne;
    private Gift giftTwo;
    private Gift gifThree;

    GiftFactory giftFactory;
    @Before
    public void setUp() {

        giftFactory = new GiftFactory();

        giftOne = new Gift("test_one", 1.00);
        giftTwo = new Gift("test_two", 2.00);
        gifThree = new Gift("test_three", 3.00);
    }

    @Test
    public void test_gift_constructor() {
        Gift gift = new Gift("test", 1.00);
        assertEquals("test", gift.getType());
        assertEquals(1, gift.getMagic(), 0.00);
    }

    @Test
    public void test_successfully_added_gift() {
        assertEquals(
                String.format("Successfully added gift %s with magic %.2f.", giftOne.getType(), giftOne.getMagic()),
                giftFactory.createGift(giftOne));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_giftFactory_for_duplicate_items () {
        giftFactory.createGift(giftOne);
        giftFactory.createGift(giftOne);
    }

    @Test
    public void test_removeGift_for_wirking_right () {
        giftFactory.createGift(giftOne);
        assertTrue(giftFactory.removeGift(giftOne.getType()));
    }

    @Test(expected = NullPointerException.class)
    public void test_removeGift_With_empty_value () {
        giftFactory.removeGift("");
    }

    @Test
    public void test_getPresentWithLeastMagic_method () {
        giftFactory.createGift(giftOne);
        giftFactory.createGift(giftTwo);
        giftFactory.createGift(gifThree);

        assertEquals(giftOne, giftFactory.getPresentWithLeastMagic());
    }

    @Test
    public void test_getPresent_method () {
        giftFactory.createGift(giftOne);
        giftFactory.createGift(giftTwo);
        giftFactory.createGift(gifThree);

        assertEquals(giftOne, giftFactory.getPresent(giftOne.getType()));
    }

    @Test
    public void test_getPresents_method () {
        giftFactory.createGift(giftOne);
        giftFactory.createGift(giftTwo);
        giftFactory.createGift(gifThree);

        assertEquals(3, giftFactory.getPresents().size());
    }

}
