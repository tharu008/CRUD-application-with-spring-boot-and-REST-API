package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.Book;
import com.example.CRUDApplication.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    //create object of BookRepo with dependency injections. Also must autowire
    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/getAllBooks") //here the ResponseEntity use <List>/collection since it has to return a collection of books
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> bookList = new ArrayList<>();
            bookRepo.findAll().forEach(bookList::add);

            //what if the collection/library is empty
            if(bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBookById/{id}") //here the ResponseEntity returns only single book therefore only 'book'
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        //with optional can return null or a book
        Optional<Book> bookData = bookRepo.findById(id);

        if(bookData.isPresent()) {
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book bookObj = bookRepo.save(book);

        return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }

    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {

        Optional<Book> oldBookData = bookRepo.findById(id);

        Book bookObj = null;
        if (oldBookData.isPresent()) {
            Book updatedBookData = oldBookData.get();
            //if title has updated
            updatedBookData.setTitle(newBookData.getTitle());
            //if author has updated
            updatedBookData.setAuthor(newBookData.getAuthor());

            bookObj = bookRepo.save(updatedBookData);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
