package jpkr.advancedprogrammingproject.repositories;

import jpkr.advancedprogrammingproject.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {}
