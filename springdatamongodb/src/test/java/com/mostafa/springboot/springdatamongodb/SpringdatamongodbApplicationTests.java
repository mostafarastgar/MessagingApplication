package com.mostafa.springboot.springdatamongodb;

import com.mostafa.springboot.springdatamongodb.domain.Book;
import com.mostafa.springboot.springdatamongodb.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringdatamongodbApplicationTests {
    @Autowired
    private BookService bookService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void init(){
        bookService.addBook(new Book(0l, "Mostafa Book", "Mos01"));
    }

    @AfterEach
    void empty(){
        bookService.remove(0l);
    }

    @Test
    void findByAuthor() {
        Book book = bookService.findByCode("Mos01");
        assertEquals("Mos01", book.getCode());
    }

    @Test
    void findAllByAuthor() {
        List<Book> books = bookService.findAllByCode("Mos*");
        assertEquals(1, books.size());
    }
}
