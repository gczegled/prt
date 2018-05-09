package hu.inf.unideb.library.models;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class BookDAO {

        private EntityManager entityManager = new EntityManagement().getEntityManager();
        private static ObservableList<Book> books = FXCollections.observableArrayList();

        public void loadBooks() {
                books.clear();
                books.addAll(entityManager.createQuery("SELECT b FROM Book b").getResultList());
        }

        public ObservableList<String> getBookTitles() {
                return books
                        .stream()
                        .map(Book::getTitle)
                        .collect(Collectors.collectingAndThen(toList(), FXCollections::observableArrayList));
        }

        public void changeBookQuantity(Book book, int num) {
                entityManager.getTransaction().begin();
                book.setCurrentNumberOfPieces(book.getCurrentNumberOfPieces() + num);
                entityManager.getTransaction().commit();
        }

        public Book getBookByIndex(int index) {
                return books.get(index);
        }

        public Book getBookByBookId(int bookId) {
                return books
                        .stream()
                        .filter(book -> book.getBookid() == bookId)
                        .findFirst()
                        .get();
        }

        public ObservableList<Book> getBooks() {
                return books;
        }

        public void setBooks(ObservableList<Book> books) {
                BookDAO.books = books;
        }
}

