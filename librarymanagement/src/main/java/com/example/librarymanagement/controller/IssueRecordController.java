package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.IssueRecord;
import com.example.librarymanagement.service.IssueRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/issuerecords")
@RequiredArgsConstructor
public class IssueRecordController {
    private final IssueRecordService issueRecordService;;


    @PostMapping("/issuethebook/{bookid}")
    public ResponseEntity<IssueRecord> issueTheBook(
            @PathVariable Long bookId
    ){
        return ResponseEntity.ok(issueRecordService.issueTheBook(bookId));
    }

    @PostMapping("/returnthebook/{issuerecordid}")
    public ResponseEntity<IssueRecord> returnTheBook(
            @PathVariable Long issueRecordId
    ){
        return ResponseEntity.ok(issueRecordService.returnTheBook(issueRecordId));
    }

}
