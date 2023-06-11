package jpkr.advancedprogrammingproject.models;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Objects;

public class OrderItem {
    @Field(targetType = FieldType.OBJECT_ID)
    private String bookId;

    private Integer quantity;

    public OrderItem(String bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && bookId.equals(orderItem.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, quantity);
    }
}
