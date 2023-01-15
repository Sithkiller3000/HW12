package de.uni.koeln.de.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.uni.koeln.de.bookstore.datamodel.Book;
import de.uni.koeln.de.bookstore.service.BookService;

@RequestMapping("/bookStore")
@RestController
public class BookController {
	
	@Autowired
	BookService bookSer;

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks(){
		
		List<Book> books = new ArrayList<Book>();
		books = bookSer.findBooks();
		
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable int id){
		return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/oldest")
	public ResponseEntity<Book> getOldestBook(){
		
		List<Book> books = new ArrayList<Book>();
		books = bookSer.findBooks();
		int oldest = Integer.MAX_VALUE;
		int oldestId = 0;
		for(Book b : books) {
			if(b.getYearDate()<oldest) {
				oldest = b.getYearDate();
				oldestId = b.getId();
			}
		}		
		return new ResponseEntity<>(bookSer.fetchBook(oldestId).get(), HttpStatus.OK);
	}
	
	@GetMapping("/latest")
	public ResponseEntity<Book> getLatestBook(){
		
		List<Book> books = new ArrayList<Book>();
		books = bookSer.findBooks();
		int latest = Integer.MIN_VALUE;
		int latestId = 0;
		for(Book b : books) {
			if(b.getYearDate() > latest) {
				latest = b.getYearDate();
				latestId = b.getId();
			}
		}		
		return new ResponseEntity<>(bookSer.fetchBook(latestId).get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Book> addBookt(@RequestBody Book book){
		
		bookSer.addBook(book);
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Book> removeBookById(@PathVariable int id){
		Book book = bookSer.fetchBook(id).get();
		if(bookSer.deleteBook(id)) {
			return new ResponseEntity<>(book, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
		}
	}
	
}
