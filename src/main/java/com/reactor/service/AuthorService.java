package com.reactor.service;

import com.reactor.entity.Author;
import com.reactor.entity.Book;
import com.reactor.repository.AuthorRepository;
import com.reactor.repository.BookRepository;
import io.netty.handler.logging.LogLevel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple4;

import java.util.logging.Level;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    public AuthorService(final AuthorRepository authorRepository, final BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Mono<Void> testMultipleThreads() {

        Mono<Book> bookOne = bookRepository.insert(new Book("book1", 10))
                .doOnNext(e -> System.out.println(e + " is running on thread " + Thread.currentThread().getId()));
        Mono<Book> bookTwo = bookRepository.insert(new Book("book2", 10))
                .doOnNext(e -> System.out.println(e + " is running on thread " + Thread.currentThread().getId()));
        Mono<Book> bookThree = bookRepository.insert(new Book("book3", 10))
                .doOnNext(e -> System.out.println(e + " is running on thread " + Thread.currentThread().getId()));
        Mono<Book> bookFour = bookRepository.insert(new Book("book4", 10))
                .doOnNext(e -> System.out.println(e + " is running on thread " + Thread.currentThread().getId()));
        Mono<Book> bookFive = bookRepository.insert(new Book("book5", 10))
                .doOnNext(e -> System.out.println(e + " is running on thread " + Thread.currentThread().getId()));

        return Mono.zip(bookOne, bookTwo,
                bookThree, bookFour, bookFive)
                .then();

//        return Mono.zip(test(1), test(2), test(3), test(4), test(5))
//                    .then();

//        return Mono.zip(test2(1), test2(2), test2(3), test2(4), test2(5))
//                    .then();

    }



    private Mono<Integer> test2(Integer value) {
        return Mono.fromCallable(() -> value)
                    .subscribeOn(Schedulers.elastic())
                    .doOnNext(e -> System.out.println(e + " is running on thread " + Thread.currentThread().getId()));
    }


    private Mono<Integer> test(Integer value) {
        return Mono.just(value)
                .subscribeOn(Schedulers.elastic())
                .doOnNext(e -> System.out.println(e + " is running on thread " + Thread.currentThread().getId()));
    }

    private Mono<Integer> testMonoDefer() {
        return Mono.defer(() -> Mono.error(new IllegalArgumentException("sdsadasdsa")));
    }

    private Mono<Integer> testMonoCallable() {
        return Mono.error(new IllegalArgumentException("adadsad"));
    }

}
