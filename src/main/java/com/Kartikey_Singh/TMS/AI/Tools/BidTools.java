package com.Kartikey_Singh.TMS.AI.Tools;

import com.Kartikey_Singh.TMS.entity.Bid;
import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import com.Kartikey_Singh.TMS.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BidTools {

    private final BidService bidService;

    @Tool(description = "Get complete details of a specific bid using its bid ID")
    public Bid getBidById(UUID bidId) {

        return bidService.getBidById(bidId);
    }

    @Tool(description = """
            Find bids using optional filters.
            Use this when the user wants to search or list bids.
            Filters can include load ID, transporter ID, and bid status.
            If a filter is not specified by the user, leave it null.
            """)
    public List<Bid> searchBids(
            UUID loadId,
            UUID transporterId,
            BidStatus status
    ) {

        return bidService.getBidsByFilter(
                loadId,
                transporterId,
                status
        );
    }

    @Tool(description = """
            Get bids for a load ranked from best to worst according to
            the TMS bidding score, which considers proposed rate and
            transporter rating.
            """)
    public List<Bid> getBestBids(UUID loadId) {

        return bidService.BestBids(loadId);
    }

    @Tool(description = """
    Submit a bid for a specific load on behalf of a transporter.
    Use this when the user wants to place or submit a bid.
    The bid must specify the load, transporter, proposed rate,
    truck type, and number of trucks offered.
    The system validates that the load is eligible for bidding
    and that the transporter has sufficient available truck capacity.
    """)
    public Bid submitBid(Bid bid)
    {
        return bidService.submitBid(bid);
    }

    @Tool(description = """
    Reject an existing bid using its bid ID.
    Use this when the user explicitly asks to reject a bid.
    The bid status will be changed to REJECTED.
    """)
    public String rejectBid(UUID bidID)
    {
        bidService.rejectBid(bidID);
        return "Your bid for bidID:"+bidID +"is successfully rejected";
    }


}