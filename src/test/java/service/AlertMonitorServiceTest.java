package service;

import com.stockportfolio.entity.Holding;
import com.stockportfolio.entity.User;
import com.stockportfolio.repository.HoldingRepository;
import com.stockportfolio.repository.UserRepository;
import com.stockportfolio.service.AlertMonitorService;
import com.stockportfolio.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlertMonitorServiceTest {

    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AlertMonitorService alertMonitorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckAlerts_SendEmail_WhenAboveThresholdCrossed() {
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setStockSymbol("AAPL");
        holding.setCurrent_price(160.0);
        holding.setAbove(150.0);
        holding.setBelow(null);
        holding.setAlert("ON");

        User user = new User();
        user.setId((int) 1L);
        user.setemail("user@example.com");

        when(holdingRepository.findByAlert("ON")).thenReturn(List.of(holding));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        alertMonitorService.checkAlerts();

        verify(emailService, times(1)).sendAlertMail(
                eq("user@example.com"),
                eq("Stock Alert: AAPL"),
                contains("160.00")
        );
    }

    @Test
    void testCheckAlerts_NoEmail_WhenNoConditionMatched() {
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setStockSymbol("AAPL");
        holding.setCurrent_price(100.0);
        holding.setAbove(150.0);
        holding.setBelow(90.0);
        holding.setAlert("ON");

        when(holdingRepository.findByAlert("ON")).thenReturn(List.of(holding));

        alertMonitorService.checkAlerts();

        verify(emailService, never()).sendAlertMail(any(), any(), any());
    }

    @Test
    void testCheckAlerts_SendEmail_WhenBelowThresholdCrossed() {
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setStockSymbol("AAPL");
        holding.setCurrent_price(80.0);
        holding.setAbove(null);
        holding.setBelow(90.0);
        holding.setAlert("ON");

        User user = new User();
        user.setId((int) 1L);
        user.setemail("user@example.com");

        when(holdingRepository.findByAlert("ON")).thenReturn(List.of(holding));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        alertMonitorService.checkAlerts();

        verify(emailService, times(1)).sendAlertMail(
                eq("user@example.com"),
                eq("Stock Alert: AAPL"),
                contains("80.00")
        );
    }
}
