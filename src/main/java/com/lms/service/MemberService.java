package com.lms.service;

import com.lms.constant.BookConstant;
import com.lms.enums.BookStatus;
import com.lms.enums.ErrorEnum;
import com.lms.exception.BookItemException;
import com.lms.exception.MemberException;
import com.lms.model.BookItem;
import com.lms.model.Member;
import com.lms.model.dto.BookItemResponse;
import com.lms.model.dto.BookStatusResponse;
import com.lms.repository.BookItemRepository;
import com.lms.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final BookItemRepository bookItemRepository;




    /**
     * this method reserve book if not available.
     * @param userid
     * @param bookItemId
     * @return ResponseEntity<BookStatusResponse>
     */
    @Transactional
    public ResponseEntity<BookStatusResponse> reserveBookItem(Long userid,Long bookItemId){

        try{
            BookItem bookitem = bookItemRepository.findById(bookItemId).orElseThrow(() -> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));

            if(bookitem.getReservedBy()==null||bookitem.getReservedBy()==userid ){

                bookitem.setReservedBy(userid);

                bookitem.setStatus(BookStatus.RESERVED.toString());

                bookItemRepository.save(bookitem);

                log.info("reserved book to user {}",userid);
                return new ResponseEntity<>(new BookStatusResponse(bookitem.getBook().getTitle(),bookitem.getStatus()),HttpStatus.OK);

            }
            log.info("reserved by another member");
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception exception){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * this method renew book.
     * @param userid
     * @param bookItemId
     * @return ResponseEntity<BookItemResponse>
     */
     public ResponseEntity<BookItemResponse> renewBook(Long userid,Long bookItemId){

        BookItem bookitem = bookItemRepository.findById(bookItemId).orElseThrow(() -> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));

        LocalDate currentDate = LocalDate.now();
        bookitem.setIssueDate(currentDate);
        bookitem.setDueDate(currentDate.plusDays(BookConstant.MAX_LENDING_DAYS));
        bookItemRepository.save(bookitem);

        log.info("renew already issued book to user ");
        return new ResponseEntity<>(new BookItemResponse(bookitem.getBook().getTitle(),bookitem.getDueDate(),"Renew"),HttpStatus.OK);


    }

    /**
     * this method  follow processing of return book
     * @param userId
     * @param bookItemId
     * @return ResponseEntity<BookStatusResponse>
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
     * issue book to a particular user
     *
     * @param userId
     * @param bookItemId
     * @return ResponseEntity<BookStatusResponse>
     */
    @Transactional
    public ResponseEntity<BookStatusResponse> issueBookItem(Long userId, Long bookItemId) {

        try {
            BookItem bookItem = bookItemRepository.findById(bookItemId).orElseThrow(()-> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(),HttpStatus.NOT_FOUND));
            Member member = memberRepository.findById(userId).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));
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



}
