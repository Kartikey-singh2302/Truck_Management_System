package com.Kartikey_Singh.TMS.AI.Tools;

import com.Kartikey_Singh.TMS.dto.BookingDto;
import com.Kartikey_Singh.TMS.entity.Booking;
import com.Kartikey_Singh.TMS.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingTools {

    private final BookingService bookingService;

    @Tool(description = "Get complete details of a booking using its booking ID")
    public Booking getBookingById(UUID bookingId) {

        return bookingService.getBookingById(bookingId);
    }

    @Tool(description = """
            Accept a bid and create a confirmed booking for that bid.
            Use this when the user wants to accept a specific bid and book
            the associated load. The operation validates load status,
            transporter capacity, and updates the bid and load status.
            """)
    public BookingDto acceptBidAndBook(UUID bidId)
    {
        return bookingService.acceptBidAndBook(bidId);
    }

    @Tool(description = """
            Cancel an existing booking using its booking ID.
            Use this when the user explicitly asks to cancel a booking.
            The operation also restores the allocated trucks to the transporter
            and updates the load status when applicable.
            """)
    public String cancelBooking(UUID bookingId) {

        bookingService.cancelBooking(bookingId);

        return "Booking " + bookingId + " has been cancelled successfully.";
    }
}