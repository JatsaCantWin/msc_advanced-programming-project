package jpkr.advancedprogrammingproject.services;

import jpkr.advancedprogrammingproject.models.*;
import jpkr.advancedprogrammingproject.repositories.BookRepository;
import jpkr.advancedprogrammingproject.repositories.OrderRepository;
import jpkr.advancedprogrammingproject.repositories.UserAccountRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    private final OrderRepository orderRepository;
    private final UserAccountRepository userAccountRepository;
    private final BookRepository bookRepository;

    public CartService(
            OrderRepository orderRepository,
            UserAccountRepository userAccountRepository,
            BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.userAccountRepository = userAccountRepository;
        this.bookRepository = bookRepository;
    }

    private Order findCartByUser(
            String username,
            boolean createIfNotExists) throws Exception {
        UserAccount user = userAccountRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("User \"" + username + "\" not found.");
        }

        Order exampleOrder = new Order(user.getId(), OrderType.CART, null);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "items");
        List<Order> foundOrders = orderRepository.findAll(Example.of(exampleOrder, matcher));

        if (foundOrders.size() >= 1) {
            // Returning an existing cart.
            return foundOrders.get(0);
        }
        if (createIfNotExists) {
            // Creating a new cart (new modifiable order).
            return orderRepository.save(exampleOrder);
        }
        throw new Exception("Could not get the cart for user \"" + username + "\".");
    }

    public List<OrderItem> getCartByUser(String username) throws Exception {
        final Order order = findCartByUser(username, true);
        if (null != order) {
            return order.getItems();
        }
        return null;
    }

    public void addToCart(String username, String bookId, Integer quantity) throws Exception {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity of added items shall be >= 1.");
        }

        final Order order = findCartByUser(username, true);
        boolean addNewBook = true;
        List<OrderItem> items = order.getItems();

        if (null != items) {
            Optional<OrderItem> foundItem = items.stream()
                    .filter(item -> item.getBookId().equals(bookId))
                    .findFirst();
            if (foundItem.isPresent()) {
                addNewBook = false;
                OrderItem item = foundItem.get();
                item.setQuantity(item.getQuantity() + quantity);
            }
        } else {
            items = new ArrayList<>();
            order.setItems(items);
        }

        if (addNewBook) {
            Optional<Book> foundBook = bookRepository.findById(bookId);
            if (foundBook.isEmpty()) {
                throw new Exception("Book with ID=\"" + bookId + "\" not found in the database.");
            }
            items.add(new OrderItem(bookId, quantity));
        }

        orderRepository.save(order);
    }

    public void removeFromCart(String username, String bookId, Integer quantity) throws Exception {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity of removed items shall be >= 0.");
        }

        final Order order = findCartByUser(username, false);
        List<OrderItem> items = order.getItems();

        if ((null == items) || (items.size() == 0)) {
            throw new Exception("Cannot remove anything from an empty cart.");
        }

        Optional<OrderItem> foundItem = items.stream()
                .filter(item -> item.getBookId().equals(bookId))
                .findFirst();
        if (foundItem.isEmpty()) {
            throw new Exception("Book with ID=\"" + bookId + "\" not found in the cart.");
        }

        OrderItem item = foundItem.get();
        if (0 == quantity) {
            item.setQuantity(0);
        } else {
            item.setQuantity(item.getQuantity() - quantity);
        }

        if (item.getQuantity() <= 0) {
            items.remove(item);
        }

        orderRepository.save(order);
    }

    public void submitOrder(String username) throws Exception {
        final Order order = findCartByUser(username, false);
        if (null != order) {
            List<OrderItem> items = order.getItems();
            if ((null == items) || (items.size() == 0)) {
                throw new Exception("Cannot place an order with an empty cart.");
            }
            order.setOrderType(OrderType.PLACED);
            orderRepository.save(order);
        }
    }
}
