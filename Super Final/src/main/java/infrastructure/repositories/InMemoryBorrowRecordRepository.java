package infrastructure.repositories;

import domain.BorrowRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryBorrowRecordRepository implements BorrowRecordRepository {
    private List<BorrowRecord> borrowRecords = new ArrayList<>();

    @Override
    public void save(BorrowRecord record) {
        borrowRecords.add(record);
    }

    @Override
    public BorrowRecord findByUserIdAndBookId(String userId, String bookId) {
        return borrowRecords.stream().filter(record -> record.getUserId().equals(userId) && record.getBookId().equals(bookId)).findFirst().orElse(null);
    }

    @Override
    public List<BorrowRecord> findByUserId(String userId) {
        return borrowRecords.stream().filter(record -> record.getUserId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public void delete(BorrowRecord record) {
        borrowRecords.remove(record);
    }
}