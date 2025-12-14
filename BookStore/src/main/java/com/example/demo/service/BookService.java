package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import entity.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository=bookRepository;
	}
	
	 public List<Book> getAllBooks() {
	        return bookRepository.findAll();
	    }

	    public Optional<Book> getBookById(String id) {
	        return bookRepository.findById(id);
	    }
	    
	    public Book createBook(Book book) {
	        return bookRepository.save(book);
	    }

	    public Optional<Book> updateBook(String id, Book bookDetails) {
	        return bookRepository.findById(id).map(book -> {
	            book.setTitle(bookDetails.getTitle());
	            book.setAuthor(bookDetails.getAuthor());
	            book.setPrice(bookDetails.getPrice());
	            return bookRepository.save(book);
	        });
	    }
	    
	    public boolean deleteBook(String id) {
	        if (bookRepository.existsById(id)) {
	            bookRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }
}
