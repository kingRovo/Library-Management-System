package com.lms.service;

import com.lms.constant.BookConstant;
import com.lms.enums.BookStatus;
import com.lms.enums.ErrorEnum;
import com.lms.exception.BookItemException;
import com.lms.exception.MemberException;
import com.lms.model.Book;
import com.lms.model.BookItem;
import com.lms.model.Librarian;
import com.lms.model.Member;
import com.lms.model.dto.BookStatusResponse;
import com.lms.repository.BookItemRepository;
import com.lms.repository.BookRepository;
import com.lms.repository.LibrarianRepository;
import com.lms.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class LibrarianService {

    private final LibrarianRepository librarianRepository;
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;
    private final MemberRepository memberRepository;


    /**
     * registering new librarian.
     *
     * @param librarian
     * @return ResponseEntity<Librarian>
     */
    @Transactional
    public ResponseEntity<Librarian> registerLibrarian(Librarian librarian) {
        if (!ObjectUtils.isEmpty(librarian)) {

            return new ResponseEntity<>(librarianRepository.save(librarian), HttpStatus.CREATED);
        } else {
            log.error("unable to register librarian. ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * add new book to library
     *
     * @param book
     * @return ResponseEntity<Book>
     */

    @Transactional
    public ResponseEntity<Book> addBook(Long id,Book book) {
        if (!ObjectUtils.isEmpty(book)) {
           // librarianRepository.findById(id).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));

            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
        } else {
            log.error("bad request unable to add book to system");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * adding new item to book.
     *
     * @param book_id
     * @param bookItem
     * @return ResponseEntity<BookItem>
     */
    @Transactional
    public ResponseEntity<BookItem> addBookItem(Long id,Long book_id, BookItem bookItem) {

        try {
            if (!ObjectUtils.isEmpty(bookItem) && book_id != null) {
                librarianRepository.findById(id).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));
                Book book = bookRepository.findById(book_id).orElseThrow(() -> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));
                bookItem.setBook(book);
                return new ResponseEntity<>(bookItemRepository.save(bookItem), HttpStatus.CREATED);
            } else {
                log.error("bad request unable to add book to system");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {

            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * this method modifying book item details.
     *
     * @param bookItem
     * @return ResponseEntity<BookItem>
     */
    @Transactional
    public ResponseEntity<BookItem> modifyBookItem(Long id,BookItem bookItem) {

        if (!ObjectUtils.isEmpty(bookItem)) {
            librarianRepository.findById(id).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));
            return new ResponseEntity<>(bookItemRepository.save(bookItem), HttpStatus.CREATED);
        } else {
            log.error("bad request unable to update book item");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * add new book to library
     *
     * @param member
     * @return ResponseEntity<Member>
     */
    @Transactional
    public ResponseEntity<Member> addNewMember(Long id,Member member) {
        if (!ObjectUtils.isEmpty(member)) {

            librarianRepository.findById(id).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));

            return new ResponseEntity<>(memberRepository.save(member), HttpStatus.CREATED);
        } else {
            log.error("unable to add new member");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * issue book to a particular user
     *
     * @param id
     * @param userId
     * @param bookItemId
     * @return ResponseEntity<BookStatusResponse>
     */
    @Transactional
    public ResponseEntity<BookStatusResponse> issueBookItem(Long id,Long userId, Long bookItemId) {

        try {
            librarianRepository.findById(id).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));
            Member member = memberRepository.findById(userId).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));

            BookItem bookItem = bookItemRepository.findById(bookItemId).orElseThrow(()-> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(),HttpStatus.NOT_FOUND));

            if(bookItem.getStatus().equalsIgnoreCase("AVAILABLE")&& member.getBookItems().size() < BookConstant.MAX_BOOKS_ISSUED ){
            bookItem.setMember(member);
            bookItem.setStatus(BookStatus.NOT_AVAILABLE.toString());
            bookItemRepository.save(bookItem);
            log.info("issued book to {}", userId);

            return new ResponseEntity<>(new BookStatusResponse(bookItem.getBook().getTitle(),bookItem.getStatus()),HttpStatus.OK);

            }
            else {
                return new ResponseEntity<>(new BookStatusResponse(bookItem.getBook().getTitle(),bookItem.getStatus()),HttpStatus.OK);
            }

        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * this method  follow processing of return book
     * @param userId
     * @param bookItemId
     * @return
     */
    @Transactional
    public  ResponseEntity<BookStatusResponse> returnBook(Long userId,Long bookItemId){

        try{

            BookItem bookItem = bookItemRepository.findById(bookItemId).orElseThrow(()-> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(),HttpStatus.NOT_FOUND));
            bookItem.setStatus(BookStatus.AVAILABLE.toString());
            bookItem.setMember(null);
            bookItemRepository.save(bookItem);
            log.info("now book {} is available ",bookItemId);
            return new ResponseEntity<>(new BookStatusResponse(bookItem.getBook().getTitle(),bookItem.getStatus()),HttpStatus.OK);

        }
        catch (Exception exception){
            throw new BookItemException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * reserved book only when book is not available in library
     * @param bookItemId
     * @return
     */
    @Transactional
    public ResponseEntity<BookStatusResponse> reservedBook(Long bookItemId,Long userId){

        try{
            BookItem book = bookItemRepository.findById(bookItemId).orElseThrow(() -> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));

            if(book.getStatus().equalsIgnoreCase(BookStatus.NOT_AVAILABLE.toString())){

                book.setReservedBy(userId);
                log.info("book reserved");

                return new ResponseEntity<>(new BookStatusResponse(book.getBook().getTitle(),BookStatus.RESERVED.toString()),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new BookStatusResponse(book.getBook().getTitle(),book.getStatus()),HttpStatus.OK);
            }
        }
        catch (Exception exception){
            throw new BookItemException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }




}
