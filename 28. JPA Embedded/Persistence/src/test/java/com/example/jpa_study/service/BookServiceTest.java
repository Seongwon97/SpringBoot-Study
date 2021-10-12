package com.example.jpa_study.service;

import com.example.jpa_study.entity.Book;
import com.example.jpa_study.repository.AuthorRepository;
import com.example.jpa_study.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void transactionTest() {
        try {
            bookService.putBookAndAuthor();
        } catch (RuntimeException e) {
            System.out.println(">>> "+ e.getMessage());
        }

        System.out.println("book : "+ bookRepository.findAll());
        System.out.println("author : "+ authorRepository.findAll());
    }

    @Test
    void isolationTest() {
        Book book = new Book();
        book.setName("JPA 강의");
        bookRepository.save(book);

        bookService.get(1L);

        System.out.println(">>> "+bookRepository.findAll());
    }

    @Test
    void convertErrorTest() {
        bookService.getAll();

        bookRepository.findAll().forEach(System.out::println);
    }
}