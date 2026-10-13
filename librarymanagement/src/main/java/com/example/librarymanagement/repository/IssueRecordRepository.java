package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
}
