package com.reactor.service;

import com.reactor.controller.dto.BookDTO;
import com.reactor.entity.Book;
import com.reactor.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Flux<BookDTO> getAllBooks() {
        return bookRepository.findAll().map(this::fromEntityToDTO);
    }

    public Mono<BookDTO> insertBook(final BookDTO dto) {
        return Mono.just(dto)
                    .map(this::fromDTOToEntity)
                    .flatMap(bookRepository::insert)
                    .map(this::fromEntityToDTO);
    }

    private BookDTO fromEntityToDTO(final Book book) {
        return new BookDTO(book.getId(), book.getName(), book.getPages());
    }

    private Book fromDTOToEntity(final BookDTO bookDTO) {
        return new Book(bookDTO.getName(), bookDTO.getPages());
    }
}
