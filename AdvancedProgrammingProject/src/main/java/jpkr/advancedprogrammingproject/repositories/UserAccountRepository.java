package jpkr.advancedprogrammingproject.repositories;

import jpkr.advancedprogrammingproject.models.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

    UserAccount findByUsername(String username);
}
