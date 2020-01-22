package com.mostafa.springboot.springdatamongodb.repository;

import com.mostafa.springboot.springdatamongodb.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {
    @Query("{code:'?0'}")
    Optional<Book> findCustomBookByCode(String code);

    @Query("{code: { $regex: ?0 } })")
    List<Book> findCustomAllByRegExCode(String code);
}
