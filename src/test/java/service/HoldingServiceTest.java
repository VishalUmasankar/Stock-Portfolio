package service;

import com.stockportfolio.entity.Activity;
import com.stockportfolio.entity.Holding;
import com.stockportfolio.entity.User;
import com.stockportfolio.repository.ActivityRepository;
import com.stockportfolio.repository.HoldingRepository;
import com.stockportfolio.repository.UserRepository;
import com.stockportfolio.service.HoldingService;
import com.stockportfolio.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HoldingServiceTest {

    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private HoldingService holdingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSellStock_PartialSell() {
        Holding existing = new Holding();
        User user = new User();
        user.setId((int) 1L);
        existing.setUserDetails(user);
        existing.setStockSymbol("GOOGL");
        existing.setQuantity(10);
        existing.setCurrent_price(100.0);

        Holding sellRequest = new Holding();
        sellRequest.setUserDetails(user);
        sellRequest.setStockSymbol("GOOGL");
        sellRequest.setQuantity(5);

        when(holdingRepository.findByUserDetails_IdAndStockSymbol(1L, "GOOGL"))
                .thenReturn(Optional.of(existing));

        String result = holdingService.sellStock(sellRequest);
        assertEquals("Stock sold successfully.", result);
        verify(holdingRepository).save(existing);
        verify(activityRepository).save(any(Activity.class));
    }

    @Test
    void testViewPortfolio() {
        User user = new User();
        user.setId((int) 1L);
        Holding h1 = new Holding();
        h1.setStockSymbol("TSLA");

        when(holdingRepository.findAllByUserDetails_Id(1L)).thenReturn(List.of(h1));
        List<Holding> holdings = holdingService.viewPortfolio(1L);
        assertEquals(1, holdings.size());
        assertEquals("TSLA", holdings.get(0).getStockSymbol());
    }

    @Test
    void testToggleAlert() {
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setAlert("OFF");

        when(holdingRepository.findById(1L)).thenReturn(Optional.of(holding));

        String result = holdingService.toggleAlert(1L, true);
        assertEquals("Alert status updated to: ON", result);
        assertEquals("ON", holding.getAlert());
    }

    @Test
    void testUpdatePrice() {
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setCurrent_price(200.0);

        when(holdingRepository.findById(1L)).thenReturn(Optional.of(holding));

        String result = holdingService.updatePrice(1L, 220.0);
        assertEquals("Price updated to: 220.0", result);
        assertEquals(220.0, holding.getCurrent_price());
    }
}
