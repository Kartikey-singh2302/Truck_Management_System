package com.Kartikey_Singh.TMS.controller;

import com.Kartikey_Singh.TMS.dto.BookingDto;
import com.Kartikey_Singh.TMS.entity.Booking;
import com.Kartikey_Singh.TMS.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/accept/{bidId}")
    public ResponseEntity<BookingDto> acceptBidAndBook(@PathVariable UUID bidId) {

        return ResponseEntity.ok(bookingService.acceptBidAndBook(bidId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable UUID bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
    }


