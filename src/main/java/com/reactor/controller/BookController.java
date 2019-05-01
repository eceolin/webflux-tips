package com.reactor.controller;

import com.reactor.controller.dto.BookDTO;
import com.reactor.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Flux<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Mono<ResponseEntity<BookDTO>> insertBook(@RequestBody final BookDTO bookDTO) {
        return bookService.insertBook(bookDTO).map(ResponseEntity.status(HttpStatus.CREATED)::body);
    }
}
