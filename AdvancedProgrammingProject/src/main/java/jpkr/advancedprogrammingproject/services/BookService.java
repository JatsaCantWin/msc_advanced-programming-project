package jpkr.advancedprogrammingproject.services;

import jpkr.advancedprogrammingproject.models.Book;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
    private final MongoTemplate mongoTemplate;

    public BookService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Book saveBook(Book book) throws Exception {
        if (!book.validate()) {
            throw new Exception("Cannot store this Book: required fields are missing.");
        }
        return mongoTemplate.save(book);
    }

    public List<Book> getAllBooks() {
        return mongoTemplate.findAll(Book.class);
    }

    public List<Book> getFilteredBooks(String author, Integer releaseYear, String title) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (author != null) {
            criteriaList.add(Criteria.where("author").regex(author, "i"));
        }
        if (releaseYear != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(releaseYear, 0, 1, 0, 0, 0);
            Date startDate = calendar.getTime();
            calendar.set(releaseYear, 11, 31, 23, 59, 59);
            Date endDate = calendar.getTime();

            criteriaList.add(Criteria.where("releaseDate").gte(startDate).lte(endDate));
        }
        if (title != null) {
            criteriaList.add(Criteria.where("title").regex(title, "i"));
        }

        Criteria criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        Query query = new Query(criteria);

        return mongoTemplate.find(query, Book.class);
    }
}
