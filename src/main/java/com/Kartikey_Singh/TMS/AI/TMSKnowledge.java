package com.Kartikey_Singh.TMS.AI;


public final class TMSKnowledge {

    private TMSKnowledge() {}

    public static final String LOAD_CANCELLATION_POLICY = """
            TMS Load Cancellation Policy

            A load can be cancelled only if it has not been booked.
            A load that is already BOOKED cannot be cancelled.
            If a load is cancelled, its status is changed to CANCELLED.
            """;

    public static final String BID_POLICY = """
            TMS Bid Policy

            A bid can be submitted only for an active load.
            Bids cannot be submitted on CANCELLED or BOOKED loads.
            A transporter must have sufficient available truck capacity
            for the truck type being offered.
            """;

    public static final String BOOKING_POLICY = """
            TMS Booking Policy

            A bid can be accepted to create a booking.
            The transporter must have sufficient available truck capacity.
            When a booking is confirmed, the allocated trucks are deducted
            from the transporter's available capacity.
            """;
}