package com.example.demo.service;

import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        log.info("Buscando todos los libros");
        return this.bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        log.info("Buscando libro por ID: {}", id);
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id.toString(), "ID"));
    }

    @Transactional
    public Book create(Book book) {
        log.info("Creando nuevo libro: {}", book);
        return this.bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, Book book) {
        log.info("Actualizando libro con ID: {}", id);
        Book existingBook = findById(id);
        book.setId(existingBook.getId());
        return this.bookRepository.save(book);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando libro con ID: {}", id);
        Book book = findById(id);
        this.bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public Book findByIsbn(String isbn) {
        log.info("Buscando libro por ISBN: {}", isbn);
        return this.bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn, "ISBN"));
    }
} 