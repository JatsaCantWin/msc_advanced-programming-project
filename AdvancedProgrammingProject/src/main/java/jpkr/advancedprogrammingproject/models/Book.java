package jpkr.advancedprogrammingproject.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String title;

    private String author;

    @Field(targetType = FieldType.DATE_TIME)
    private Date releaseDate;

    private String ISBN;

    public Book(String title, String author, Date releaseDate, String ISBN) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.ISBN = ISBN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean validate() {
        return
            (null != title) && (!title.isBlank()) &&
            (null != author) && (!author.isBlank()) &&
            (null != releaseDate) &&
            (null != ISBN) && (!ISBN.isBlank());
    }
}
