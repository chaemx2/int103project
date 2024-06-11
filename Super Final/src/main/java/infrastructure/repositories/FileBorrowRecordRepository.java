package infrastructure.repositories;

import domain.BorrowRecord;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBorrowRecordRepository implements BorrowRecordRepository {
    private final String filename = "borrow_records.txt";
    private Map<String, BorrowRecord> borrowRecords = new HashMap<>();

    public FileBorrowRecordRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String userId = parts[0];
                    String bookId = parts[1];
                    LocalDate borrowDate = LocalDate.parse(parts[2]);
                    LocalDate dueDate = LocalDate.parse(parts[3]);
                    borrowRecords.put(userId + "-" + bookId, new BorrowRecord(userId, bookId, borrowDate, dueDate));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading borrow records from file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (BorrowRecord record : borrowRecords.values()) {
                writer.write(record.getUserId() + "," + record.getBookId() + "," + record.getBorrowDate() + "," + record.getDueDate());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving borrow records to file: " + e.getMessage());
        }
    }

    @Override
    public void save(BorrowRecord borrowRecord) {
        borrowRecords.put(borrowRecord.getUserId() + "-" + borrowRecord.getBookId(), borrowRecord);
        saveToFile();
    }

    @Override
    public List<BorrowRecord> findByUserId(String userId) {
        List<BorrowRecord> records = new ArrayList<>();
        for (BorrowRecord record : borrowRecords.values()) {
            if (record.getUserId().equals(userId)) {
                records.add(record);
            }
        }
        return records;
    }

    @Override
    public BorrowRecord findByUserIdAndBookId(String userId, String bookId) {
        return borrowRecords.get(userId + "-" + bookId);
    }

    @Override
    public void delete(BorrowRecord borrowRecord) {
        borrowRecords.remove(borrowRecord.getUserId() + "-" + borrowRecord.getBookId());
        saveToFile();
    }
}