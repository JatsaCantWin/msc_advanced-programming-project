package jpkr.advancedprogrammingproject.controllers;

import jpkr.advancedprogrammingproject.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(
            Authentication authentication) {
        try
        {
            return ResponseEntity.ok(
                orderService.getUserOrders(authentication.getName()));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getUserOrders(
            Authentication authentication,
            @PathVariable String orderId) {
        try
        {
            if ((null == orderId) || orderId.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(
                orderService.getSingleOrder(authentication.getName(), orderId));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
