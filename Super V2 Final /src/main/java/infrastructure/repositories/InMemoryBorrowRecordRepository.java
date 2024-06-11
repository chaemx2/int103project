package infrastructure.repositories;

import domain.BorrowRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryBorrowRecordRepository implements BorrowRecordRepository {
    private Map<String, BorrowRecord> borrowRecords = new HashMap<>();

    @Override
    public void save(BorrowRecord borrowRecord) {
        borrowRecords.put(borrowRecord.getBookId() + "_" + borrowRecord.getUserId(), borrowRecord);
    }

    @Override
    public BorrowRecord findByUserIdAndBookId(String userId, String bookId) {
        return borrowRecords.get(bookId + "_" + userId);
    }

    @Override
    public List<BorrowRecord> findByUserId(String userId) {
        return borrowRecords.values().stream()
                .filter(record -> record.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(BorrowRecord borrowRecord) {
        borrowRecords.remove(borrowRecord.getBookId() + "_" + borrowRecord.getUserId());
    }
}