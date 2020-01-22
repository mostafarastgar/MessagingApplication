package com.mostafa.springboot.springdatamongodb.service;

import com.mostafa.springboot.springdatamongodb.domain.Book;
import com.mostafa.springboot.springdatamongodb.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public void remove(long id){
        bookRepository.deleteById(id);
    }

    public Book findByCode(String code){
        return bookRepository.findCustomBookByCode(code).orElseThrow();
    }

    public List<Book> findAllByCode(String code){
        return bookRepository.findCustomAllByRegExCode(code);
    }

}
