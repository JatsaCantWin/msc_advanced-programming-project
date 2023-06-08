# Advanced Programming Assignments

This repository contains the final project I and my colleague did as part of the advanced programming course I attended during my MSc degree studies. The code in this repository is intended for educational purposes only. As a result, it may be rough and unpolished, as it was never refactored.

## Technology used
- Spring Flux framework
- Spring Security
- MongoDB

## Specification

### Database entities
- Book
    - title: String
    - author: String
    - releaseDate: Date
    - ISBN: String
- Cart
    - userId: Integer
    - items: Array
        - bookId: Integer
        - quantity: Integer
- Order
    - userId: Integer
    - items: Array
        - bookId: Integer
        - quantity: Integer

### Enpoints
- /books
    - GET: query all books in the database
- /books/filter?author=Author&releaseYear=YYYY&title=Title
    - GET: query all books, filtered by their title/author/year of release
- /user/cart
    - GET: query all books in the cart of the currently logged in user
    - POST: add books to the cart
        ```json
        {
            bookId: Integer
            quantity: Integer (optional, by deafult 1)
        }
        ```
    - DELETE: remove books from the cart
        ```json
        {
            bookId: Integer
            quantity: Integer (optional, by default remove everything)
        }
        ```
- /user/cart/submit
    - POST: submit an order based on the items in the cart
- /orders
    - GET: query all orders submitted by the current user
- /orders/:orderId
    - GET: query a specific order by id

## Contributing

As this code is intended for educational purposes only, contributions are not necessary.
