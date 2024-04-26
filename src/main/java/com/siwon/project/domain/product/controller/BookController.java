package com.siwon.project.domain.product.controller;

import com.siwon.project.domain.product.dto.BookInfoRequestDTO;
import com.siwon.project.domain.product.dto.BookInfoResponseDTO;
import com.siwon.project.domain.product.dto.NewBookRequestDTO;
import com.siwon.project.domain.product.dto.UpdateBookInfoRequestDTO;
import com.siwon.project.domain.product.service.BookService;
import com.siwon.project.global.common.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> addNewBook(
            @RequestBody NewBookRequestDTO newBookRequestDTO
    ) {
        bookService.newBook(newBookRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("책 추가 성공", null));
    }
    @PatchMapping("/{bookId}")
    public ResponseEntity<CommonResponse<BookInfoResponseDTO>> updateBookInfo(
            @RequestBody UpdateBookInfoRequestDTO updateBookInfoRequestDTO,
            @PathVariable(name = "bookId")Long bookId
    ){
        BookInfoResponseDTO bookInfo = bookService.updateBookInfo(updateBookInfoRequestDTO,bookId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of("정보 수정 성공",bookInfo));
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<CommonResponse<BookInfoResponseDTO>> getBookById(
            @PathVariable(name = "bookId")Long bookId
    ){
        BookInfoResponseDTO bookInfo = bookService.getBookInfo(bookId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of("책 조회 성공",bookInfo));
    }
    @GetMapping("/authors/{authorName}")
    public ResponseEntity<CommonResponse<List<BookInfoResponseDTO>>> getBooksByAuthor(
            @PathVariable(name = "authorName")String name
    ){
        List<BookInfoResponseDTO> books = bookService.getBookByAuthor(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of("책 조회 성공",books));
    }
    @GetMapping("/publisher/{publisherName}")
    public ResponseEntity<CommonResponse<List<BookInfoResponseDTO>>> getBooksByPublisher(
            @PathVariable(name = "publisherName")String name
    ){
        List<BookInfoResponseDTO> books = bookService.getBookByPublisher(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of("책 조회 성공",books));
    }
    @GetMapping
    public ResponseEntity<CommonResponse<BookInfoResponseDTO>> getBookByName(
            @RequestBody BookInfoRequestDTO bookInfoRequestDTO
    ){
        BookInfoResponseDTO bookInfoResponseDTO = bookService.getByBookName(bookInfoRequestDTO.getBookName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of("책 조회 성공",bookInfoResponseDTO));
    }








}
