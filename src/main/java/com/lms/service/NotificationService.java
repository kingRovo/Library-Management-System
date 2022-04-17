package com.lms.service;

import com.lms.constant.BookConstant;
import com.lms.enums.BookStatus;
import com.lms.enums.ErrorEnum;
import com.lms.exception.BookItemException;
import com.lms.exception.MemberException;
import com.lms.model.BookItem;
import com.lms.model.Fine;
import com.lms.model.Member;
import com.lms.model.dto.BookAvailableNotification;
import com.lms.model.dto.Notification;
import com.lms.repository.BookItemRepository;
import com.lms.repository.FineRepository;
import com.lms.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@Service
@Slf4j
public class NotificationService {

    private final MemberRepository memberRepository;
    private final FineRepository fineRepository;
    private final BookItemRepository bookItemRepository;


    /**
     * check fine for a user and sand notification if user have any fine.
     * @param userId
     * @return
     */
    public ResponseEntity<Notification> checkFineAndSandNotification(Long userId){

        try{

            Member member = memberRepository.findById(userId).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));


            LocalDate currentDate = LocalDate.now();



            for(BookItem bookItem:member.getBookItems()){

                Fine fine = new Fine();
                Period period = Period.between(bookItem.getDueDate(),currentDate);

                int totalDays = period.getDays();

                Double totalFine = (double) (totalDays * BookConstant.FINE_PER_DAY);
                fine.setTotalFine(totalFine);
                fine.setBookItemID(bookItem.getId());
                fine.setMemberId(userId);
                fineRepository.save(fine);
                log.info("user has total {} fine of book {}",totalFine,bookItem.getId());
                if(totalFine >= BookConstant.FINE_PER_DAY) {
                    Notification notification = new Notification(member.getName(), bookItem.getDueDate(), bookItem.getBook().getTitle(), totalFine);

                    return new ResponseEntity<>(notification,HttpStatus.OK);

                }
                log.info("user not have any fine to sand notification");
                return new ResponseEntity<>(HttpStatus.OK);


            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        }
        catch (Exception exception){

            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * sanding book available notification to user
     * @param userId
     * @param bookItemId
     * @return ResponseEntity
     */
    @Transactional
    public ResponseEntity<BookAvailableNotification> bookAvailableNotification(Long userId,Long bookItemId){

        BookItem bookitem = bookItemRepository.findById(bookItemId).orElseThrow(() -> new BookItemException(ErrorEnum.BOOK_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));
        Member member = memberRepository.findById(userId).orElseThrow(() -> new MemberException(ErrorEnum.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND));

        if (bookitem.getStatus().equalsIgnoreCase(BookStatus.AVAILABLE.toString())){
            log.info("sanding book available notification to user {}",userId);
            return  new ResponseEntity<>(new BookAvailableNotification(member.getName(),bookitem.getStatus(),bookitem.getBook().getTitle()),HttpStatus.OK);
        }
        else {
            log.debug("unable to sand notification to user {}",userId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }





}
