package domain;

public class Book {
    private String id;
    private String name;
    private String category;
    private int amount;

    public Book(String id, String name, String category, int amount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}