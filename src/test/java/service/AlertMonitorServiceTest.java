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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AlertMonitorServiceTest {

    @InjectMocks
    private AlertMonitorService alertMonitorService;

    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkAlerts_shouldSendEmailWhenPriceCrossesLimits() {
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setStockSymbol("AAPL");
        holding.setAlert("ON");
        holding.setCurrent_price(150.0);
        holding.setAbove(140.0);
        holding.setBelow(130.0);

        User user = new User();
        user.setemail("test@example.com");

        when(holdingRepository.findByAlert("ON")).thenReturn(List.of(holding));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        alertMonitorService.checkAlerts();

        verify(emailService, times(1)).sendAlertMail(eq("test@example.com"), contains("AAPL"), anyString());
    }

    @Test
    void checkAlerts_shouldNotSendEmailIfUserOrEmailNull() {
        Holding holding = new Holding();
        holding.setId(1L);
        holding.setAlert("ON");
        holding.setCurrent_price(150.0);
        holding.setAbove(140.0);

        when(holdingRepository.findByAlert("ON")).thenReturn(List.of(holding));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        alertMonitorService.checkAlerts();

        verify(emailService, never()).sendAlertMail(anyString(), anyString(), anyString());
    }
}
