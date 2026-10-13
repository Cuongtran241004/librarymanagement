package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Book Not Found"));

        return book;
    }


    public Book addBook(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setIsAvailable(bookDTO.getIsAvailable());
        book.setQuantity(bookDTO.getQuantity());

        return bookRepository.save(book);
    }


    public Book updateBook(Long id, BookDTO bookDTO){
        Book oldBook = bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Book Not Found"));

        oldBook.setTitle(bookDTO.getTitle());
        oldBook.setAuthor(bookDTO.getAuthor());
        oldBook.setIsbn(bookDTO.getIsbn());
        oldBook.setIsAvailable(bookDTO.getIsAvailable());
        oldBook.setQuantity(bookDTO.getQuantity());

        return bookRepository.save(oldBook);
    }

    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }
}
