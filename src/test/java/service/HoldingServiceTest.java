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
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HoldingServiceTest {

    @InjectMocks
    private HoldingService holdingService;

    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buyStock_shouldUpdateExistingHolding() throws Exception {
        User user = new User();
        user.setId((int) 1L);

        Holding existingHolding = new Holding();
        existingHolding.setQuantity(10);
        existingHolding.setBuyPrice(100.0);
        existingHolding.setUserDetails(user);
        existingHolding.setStockSymbol("AAPL");

        Holding request = new Holding();
        request.setQuantity(5);
        request.setBuyPrice(110.0);
        request.setStockSymbol("AAPL");
        request.setUserDetails(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(holdingRepository.findByUserDetails_IdAndStockSymbol(1L, "AAPL")).thenReturn(Optional.of(existingHolding));
        try (MockedStatic<Util> utilities = mockStatic(Util.class)) {
            utilities.when(() -> Util.getLatestPrice("AAPL")).thenReturn(115.0);
            String result = holdingService.buyStock(request);

            assertEquals("Stock bought and updated.", result);
            verify(holdingRepository).save(existingHolding);
            verify(activityRepository).save(any(Activity.class));
        }
    }

    @Test
    void sellStock_shouldThrowWhenSellQtyExceeds() {
        User user = new User();
        user.setId((int) 1L);

        Holding existing = new Holding();
        existing.setQuantity(5);
        existing.setStockSymbol("AAPL");
        existing.setUserDetails(user);
        existing.setCurrent_price(120.0);

        when(holdingRepository.findByUserDetails_IdAndStockSymbol(1L, "AAPL")).thenReturn(Optional.of(existing));

        Holding sellRequest = new Holding();
        sellRequest.setQuantity(10);
        sellRequest.setStockSymbol("AAPL");
        sellRequest.setUserDetails(user);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> holdingService.sellStock(sellRequest));
        assertEquals("Cannot sell more than you own.", ex.getMessage());
    }

    @Test
    void viewPortfolio_shouldReturnHoldingDtoList() {
        Holding holding = new Holding();
        holding.setStockSymbol("AAPL");
        holding.setQuantity(10);
        holding.setBuyPrice(100.0);
        holding.setCurrent_price(120.0);

        when(holdingRepository.findAllByUserDetails_Id(1L)).thenReturn(List.of(holding));

        var dtoList = holdingService.viewPortfolio(1L);
        assertEquals(1, dtoList.size());
        assertEquals("AAPL", dtoList.get(0).getStockSymbol());
        assertTrue(dtoList.get(0).getGain() > 0);
    }
}
