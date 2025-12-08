package com.Kartikey_Singh.TMS.controller;
import com.Kartikey_Singh.TMS.dto.BidDto;
import com.Kartikey_Singh.TMS.entity.Bid;
import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import com.Kartikey_Singh.TMS.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bid")
public class BidController {
    private final BidService bidService;

    @PostMapping
    public ResponseEntity<Bid> submitBid(@RequestBody BidDto bidDto,
                                         @RequestParam UUID loadId,
                                         @RequestParam UUID transporterId,
                                         @RequestParam String truckType)
    {
        Bid bid = new Bid();
        bid.setLoadId(loadId);
        bid.setTransporterId(transporterId);
        bid.setTruckType(truckType);
        bid.setProposedRate(bidDto.getProposedRate());
        bid.setTrucksOffered(bidDto.getTrucksOffered());
        bid.setBidStatus(BidStatus.PENDING);

        Bid savedBid = bidService.submitBid(bid);
        return ResponseEntity.ok(savedBid);
    }

    @GetMapping
    public ResponseEntity<List<Bid>> filterBids(
            @RequestParam(required = false) UUID loadId,
            @RequestParam(required = false) UUID transporterId,
            @RequestParam(required = false) BidStatus status) {

        List<Bid> bids = bidService.getBidsByFilter(loadId, transporterId, status);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/{bidId}")
    public ResponseEntity<Bid> getBidById(@PathVariable UUID bidId) {
        Bid bid = bidService.getBidById(bidId);
        return ResponseEntity.ok(bid);
    }

    @PatchMapping("/{bidId}/reject")
    public ResponseEntity<Void> rejectBid(@PathVariable UUID bidId) {
        bidService.rejectBid(bidId);
        return ResponseEntity.noContent().build();
    }
}
