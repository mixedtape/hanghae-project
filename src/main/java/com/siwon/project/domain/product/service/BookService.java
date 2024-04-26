package com.siwon.project.domain.product.service;

import com.siwon.project.domain.product.dto.BookInfoResponseDTO;
import com.siwon.project.domain.product.dto.NewBookRequestDTO;
import com.siwon.project.domain.product.dto.UpdateBookInfoRequestDTO;
import com.siwon.project.domain.product.entity.Book;
import com.siwon.project.domain.product.repository.BookRepository;
import com.siwon.project.global.exception.book.AlreadyExistBookException;
import com.siwon.project.global.exception.book.BookNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void newBook(NewBookRequestDTO newBookRequestDTO) {

        if (!bookRepository.existsByName(newBookRequestDTO.getName())) {
            throw new AlreadyExistBookException();
        }

        Book book = Book.builder()
                .bookName(newBookRequestDTO.getName())
                .bookPrice(newBookRequestDTO.getPrice())
                .description(newBookRequestDTO.getDescription())
                .publisher(newBookRequestDTO.getPublisher())
                .author(newBookRequestDTO.getAuthor())
                .build();
        bookRepository.save(book);
    }

    public BookInfoResponseDTO getByBookName(String name) {
        Book book = bookRepository.findByName(name).orElseThrow(BookNotFoundException::new);
        return BookInfoResponseDTO.fromEntity(book);
    }

    public BookInfoResponseDTO getBookInfo(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        return BookInfoResponseDTO.fromEntity(book);

    }

    public List<BookInfoResponseDTO> getBookByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return books.stream().map(BookInfoResponseDTO::fromEntity).toList();
    }

    public List<BookInfoResponseDTO> getBookByPublisher(String publisher) {
        List<Book> books = bookRepository.findByPublisher(publisher);
        return books.stream().map(BookInfoResponseDTO::fromEntity).toList();
    }

    @Transactional
    public BookInfoResponseDTO updateBookInfo(
            UpdateBookInfoRequestDTO updateBookInfoRequestDTO,
            Long bookId
    ) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        book.updateBookInfo(
                updateBookInfoRequestDTO.getName(),
                updateBookInfoRequestDTO.getPrice(),
                updateBookInfoRequestDTO.getDescription(),
                updateBookInfoRequestDTO.getAuthor(),
                updateBookInfoRequestDTO.getPublisher(),
                updateBookInfoRequestDTO.getStock());
        return BookInfoResponseDTO.fromEntity(book);

    }


}
