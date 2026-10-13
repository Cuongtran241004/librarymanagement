package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/getallbooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/getbookbyid/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/addbook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @PutMapping("/updatebook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody BookDTO bookDTO){
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }


    @DeleteMapping("/deletebook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
}
