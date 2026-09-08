package com.Kartikey_Singh.TMS.service;

import com.Kartikey_Singh.TMS.dto.BookingDto;
import com.Kartikey_Singh.TMS.entity.Booking;

import java.util.UUID;

public interface BookingService {
    BookingDto acceptBidAndBook(UUID bidId);
    Booking getBookingById(UUID bookingId);
    void cancelBooking(UUID bookingId);
}
