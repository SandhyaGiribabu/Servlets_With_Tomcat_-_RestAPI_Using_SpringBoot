package com.example.demo.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/bookStore")
public class BookController {
	
	private final BookService bookService;
	
	public BookController(BookService bookService) {
		  this.bookService=bookService;
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable String bookId){
		
		return bookService.getBookById(bookId)
                .map(book->new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getBooks(){
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
		
	}
	
	@PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }
	
	 @PutMapping("/{bookId}")
	    public ResponseEntity<Book> updateBook(@PathVariable String bookId,
	                                           @RequestBody Book bookDetails) {
	        return bookService.updateBook(bookId, bookDetails)
	                .map(updated->new ResponseEntity<>(updated, HttpStatus.OK))
	                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	
	 @DeleteMapping("/{bookId}")
	    public ResponseEntity<String> deleteBook(@PathVariable String bookId) {
	        if (bookService.deleteBook(bookId)) {
	            return new ResponseEntity<>("Book Deleted Successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Book Not Found", HttpStatus.NOT_FOUND);
	        }
	    }

}
