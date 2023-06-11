package jpkr.advancedprogrammingproject.services;

import jpkr.advancedprogrammingproject.models.Order;
import jpkr.advancedprogrammingproject.models.OrderType;
import jpkr.advancedprogrammingproject.models.UserAccount;
import jpkr.advancedprogrammingproject.repositories.BookRepository;
import jpkr.advancedprogrammingproject.repositories.OrderRepository;
import jpkr.advancedprogrammingproject.repositories.UserAccountRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserAccountRepository userAccountRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserAccountRepository userAccountRepository) {
        this.orderRepository = orderRepository;
        this.userAccountRepository = userAccountRepository;
    }

    private List<Order> findOrdersByUser(
            String username,
            String orderId) throws Exception {
        UserAccount user = userAccountRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("User \"" + username + "\" not found.");
        }

        if (null == orderId) {
            return orderRepository.findOrdersByUserIdExcludingType(
                    user.getId(),
                    OrderType.CART.name());
        } else {
            // Making sure that the user can read only the orders which they own.
            return orderRepository.findOrdersByIdFromUser(orderId, user.getId());
        }
    }

    public List<Order> getUserOrders(String username) throws Exception {
        return findOrdersByUser(username, null);
    }

    public Order getSingleOrder(String username, String orderId) throws Exception {
        List<Order> results = findOrdersByUser(username, orderId);
        if (results.size() == 0) {
            throw new Exception("Order ID=\"" + orderId + "\" not found, or does not belong to you.");
        }
        return results.get(0);
    }
}
