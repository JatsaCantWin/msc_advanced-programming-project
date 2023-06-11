package jpkr.advancedprogrammingproject.repositories;

import jpkr.advancedprogrammingproject.models.Order;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    @Query("{ 'userId': ?0, 'orderType': { $ne: ?1 } }")
    List<Order> findOrdersByUserIdExcludingType(String userId, String orderType);

    @Query("{ '_id': ?0, 'userId': ?1 }")
    List<Order> findOrdersByIdFromUser(String id, String userId);
}
