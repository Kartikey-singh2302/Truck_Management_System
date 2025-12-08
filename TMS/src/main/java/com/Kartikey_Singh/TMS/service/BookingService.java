package com.Kartikey_Singh.TMS.service;

import com.Kartikey_Singh.TMS.entity.Booking;

import java.util.UUID;

public interface BookingService {
    Booking acceptBidAndBook(UUID bidId);
    Booking getBookingById(UUID bookingId);
    void cancelBooking(UUID bookingId);
}
