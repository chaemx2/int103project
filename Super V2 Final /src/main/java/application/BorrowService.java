package application;

import domain.BorrowRecord;
import infrastructure.repositories.BorrowRecordRepository;

import java.time.LocalDate;
import java.util.List;

public class BorrowService {
    private BorrowRecordRepository borrowRecordRepository;

    public BorrowService(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public void borrowBook(String userId, String bookId) {
        BorrowRecord record = new BorrowRecord(userId, bookId, LocalDate.now(), LocalDate.now().plusDays(14));
        borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getBorrowRecordsByUser(String userId) {
        return borrowRecordRepository.findByUserId(userId);
    }

    public void returnBook(String userId, String bookId) {
        BorrowRecord record = borrowRecordRepository.findByUserIdAndBookId(userId, bookId);
        if (record == null) {
            throw new RuntimeException("Invalid book");
        }
        borrowRecordRepository.delete(record);
    }

}