package com.mostafa.springboot.elasticsearch;

import com.mostafa.springboot.elasticsearch.domain.Book;
import com.mostafa.springboot.elasticsearch.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElasticsearchApplicationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    private Book book;

    @BeforeEach
    public void before() {
        esTemplate.deleteIndex(Book.class);
        esTemplate.createIndex(Book.class);
        esTemplate.putMapping(Book.class);
        esTemplate.refresh(Book.class);
        book = new Book("1001", "Sample elasticsearch book", "Mostafa Rastegar", "This is a sample book to test elasticsearch");
    }

    @Test
    public void testSave() {

        Book testBook = bookService.save(book);

        assertNotNull(testBook.getId());
        assertEquals(testBook.getTitle(), book.getTitle());
        assertEquals(testBook.getAuthor(), book.getAuthor());
        assertEquals(testBook.getContent(), book.getContent());

    }

    @Test
    public void testFindOne() {

        bookService.save(book);

        Book testBook = bookService.findOne(book.getId());

        assertNotNull(testBook.getId());
        assertEquals(testBook.getTitle(), book.getTitle());
        assertEquals(testBook.getAuthor(), book.getAuthor());
        assertEquals(testBook.getContent(), book.getContent());

    }

    @Test
    public void testFindByTitle() {

        bookService.save(book);

        List<Book> byTitle = bookService.findByTitle(book.getTitle());
        assertThat(byTitle.size(), is(1));
    }

    @Test
    public void testFindByAuthor() {

        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("1001", "Sample elasticsearch book1", "Mostafa Rastegar", "This is a sample book1 to test elasticsearch"));
        bookList.add(new Book("1002", "Sample elasticsearch book2", "Mostafa Rastegar", "This is a sample book2 to test elasticsearch"));
        bookList.add(new Book("1003", "Sample elasticsearch book3", "Mostafa Rastegar", "This is a sample book3 to test elasticsearch"));
        bookList.add(new Book("1007", "Sample elasticsearch book4", "Mostafa Rastegar", "This is a sample book4 to test elasticsearch"));
        bookList.add(new Book("1008", "Sample elasticsearch book5", "Hannah Rastegar", "This is a sample book5 to test elasticsearch"));

        for (Book book : bookList) {
            bookService.save(book);
        }

        Page<Book> byAuthor = bookService.findByAuthor("Mostafa Rastegar", PageRequest.of(0, 10));
        assertThat(byAuthor.getTotalElements(), is(4L));

        Page<Book> byAuthor2 = bookService.findByAuthor("Hannah Rastegar", PageRequest.of(0, 10));
        assertThat(byAuthor2.getTotalElements(), is(1L));

    }

    @Test
    public void testDelete() {
        bookService.save(book);
        bookService.delete(book);
        assertThrows(NoSuchElementException.class, () -> bookService.findOne(book.getId()));
    }

}
