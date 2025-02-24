package com.example.demo.controller;

import com.example.demo.controller.dto.BookDTO;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/books")
@Tag(name = "Books", description = "API para la gestión de libros")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los libros")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = this.bookService.findAll();
        List<BookDTO> dtos = books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un libro por ID")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = this.bookService.findById(id);
        return ResponseEntity.ok(convertToDTO(book));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo libro")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = this.bookService.create(book);
        return ResponseEntity.ok(convertToDTO(savedBook));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un libro existente")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book updatedBook = this.bookService.update(id, book);
        return ResponseEntity.ok(convertToDTO(updatedBook));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un libro")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        this.bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Obtener un libro por ISBN")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable String isbn) {
        Book book = this.bookService.findByIsbn(isbn);
        return ResponseEntity.ok(convertToDTO(book));
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPrice(book.getPrice());
        dto.setPublicationDate(book.getPublicationDate());
        dto.setIsbn(book.getIsbn());
        dto.setAvailable(book.getAvailable());
        return dto;
    }

    private Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setPublicationDate(dto.getPublicationDate());
        book.setIsbn(dto.getIsbn());
        book.setAvailable(dto.getAvailable());
        return book;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Void> handleBookNotFound() {
        return ResponseEntity.notFound().build();
    }
} 