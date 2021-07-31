import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    int initialMenuSize;

    Restaurant spy_Restaurant;

    @BeforeEach
    public void initializeRestaurant() {
        openingTime = LocalTime.parse("09:30:00");
        closingTime = LocalTime.parse("23:00:00");
        restaurant = new Restaurant("Trump cafe", "USA", openingTime, closingTime);
        restaurant.addToMenu("Chicken pery pery", 450);
        restaurant.addToMenu("Italian Pizza", 350);

        initialMenuSize = restaurant.getMenu().size();

        spy_Restaurant = Mockito.spy(restaurant);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime betweenWorking = LocalTime.of(11, 30, 0);

        Mockito.when(spy_Restaurant.getCurrentTime()).thenReturn(betweenWorking);

        assertTrue(spy_Restaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime beforeOpening = LocalTime.of(7, 30, 0);

        LocalTime afterClosing = LocalTime.of(23, 30, 0);


        Mockito.when(spy_Restaurant.getCurrentTime()).thenReturn(beforeOpening, afterClosing);

        assertFalse(spy_Restaurant.isRestaurantOpen());

        assertFalse(spy_Restaurant.isRestaurantOpen());


    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Chicken Biryani",350);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.removeFromMenu("Italian Pizza");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("Chicken Tikka"));
    }
    @Test
    public void selecting_items_from_menu_return_the_total_price() {
        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Chicken pery pery");
        selectedItems.add("Italian Pizza");

        int totalPrice = restaurant.getOrderValue(selectedItems);
        assertEquals(800, totalPrice);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}