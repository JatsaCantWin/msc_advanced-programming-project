package jpkr.advancedprogrammingproject.controllers;

import jpkr.advancedprogrammingproject.models.OrderItem;
import jpkr.advancedprogrammingproject.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(
            Authentication authentication) {
        try
        {
            return ResponseEntity.ok(
                cartService.getCartByUser(authentication.getName()));
        }
        catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addToCart(
            Authentication authentication,
            @RequestBody OrderItem item) {
        try
        {
            String bookId = item.getBookId();
            if ((null == bookId) || bookId.isBlank()) {
                // Required key "bookId" is missing.
                return ResponseEntity.badRequest().build();
            }
            Integer quantity = item.getQuantity();
            if (null == quantity) {
                // Optional "quantity" key (default action: append one book).
                quantity = 1;
            }
            cartService.addToCart(authentication.getName(), bookId, quantity);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeFromCart(
            Authentication authentication,
            @RequestBody OrderItem item) {
        try
        {
            String bookId = item.getBookId();
            if ((null == bookId) || bookId.isBlank()) {
                // Required key "bookId" is missing.
                return ResponseEntity.badRequest().build();
            }
            Integer quantity = item.getQuantity();
            if (null == quantity) {
                // Optional "quantity" key (default action: remove all books).
                quantity = 0;
            }
            cartService.removeFromCart(authentication.getName(), bookId, quantity);
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitOrder(
            Authentication authentication) {
        try
        {
            cartService.submitOrder(authentication.getName());
            return ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
