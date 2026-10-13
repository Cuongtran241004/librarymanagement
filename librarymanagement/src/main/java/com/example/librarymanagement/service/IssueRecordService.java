package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.IssueRecord;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.IssueRecordRepository;
import com.example.librarymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueRecordService {
    private final BookRepository  bookRepository;
    private final UserRepository userRepository;
    private final IssueRecordRepository issueRecordRepository;

    public IssueRecord issueTheBook(Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new RuntimeException("Book not found"));

        if(book.getQuantity() <= 0 || !book.getIsAvailable()){
            throw new RuntimeException("Book is not available");
        }

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));
        issueRecord.setIsReturned(false);

        issueRecord.setBook(book);
        issueRecord.setUser(user);

        book.setQuantity(book.getQuantity() - 1);
        if(book.getQuantity() == 0 ){
            book.setIsAvailable(false);
        }
        bookRepository.save(book);

        issueRecordRepository.save(issueRecord);
        return issueRecord;
    }

    public IssueRecord returnTheBook(Long issueRecordId){
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(()-> new RuntimeException("IssueRecord not found"));

        if(issueRecord.getIsReturned()){
            throw new RuntimeException("IssueRecord is already returned");
        }

        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);
        book.setIsAvailable(true);
        bookRepository.save(book);


        issueRecord.setIsReturned(true);
        issueRecord.setReturnDate(LocalDate.now());
        return issueRecordRepository.save(issueRecord);
    }
}
