package infrastructure.repositories;

import domain.BorrowRecord;

import java.util.List;

public interface BorrowRecordRepository {
    void save(BorrowRecord record);

    BorrowRecord findByUserIdAndBookId(String userId, String bookId);

    List<BorrowRecord> findByUserId(String userId);

    void delete(BorrowRecord record);
}