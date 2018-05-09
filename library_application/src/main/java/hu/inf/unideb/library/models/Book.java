package hu.inf.unideb.library.models;

import javax.persistence.*;


@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "bookid")
    private int bookid;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "numberofpieces")
    private int numberOfPieces;
    @Column(name = "currentnumberofpieces")
    private int currentNumberOfPieces;

    public Book() {}

    public Book(int bookid, String title, String author, int numberOfPieces, int currentNumberOfPieces) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.numberOfPieces = numberOfPieces;
        this.currentNumberOfPieces = currentNumberOfPieces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public void setNumberOfPieces(int numberOfPieces) {
        this.numberOfPieces = numberOfPieces;
    }

    public int getCurrentNumberOfPieces() {
        return currentNumberOfPieces;
    }

    public void setCurrentNumberOfPieces(int currentNumberOfPieces) {
        this.currentNumberOfPieces = currentNumberOfPieces;
    }
}
