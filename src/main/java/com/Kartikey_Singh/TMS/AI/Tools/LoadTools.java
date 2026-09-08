package com.Kartikey_Singh.TMS.AI.Tools;

import com.Kartikey_Singh.TMS.AI.Entity.AIContextKeys;
import com.Kartikey_Singh.TMS.dto.CreateLoadRequest;
import com.Kartikey_Singh.TMS.entity.Load;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import com.Kartikey_Singh.TMS.entity.enums.UserRoles;
import com.Kartikey_Singh.TMS.exception.AccessDeniedException;
import com.Kartikey_Singh.TMS.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoadTools {

    private final LoadService loadService;

    @Tool(description = "Get complete details of a specific load using its load ID")
    public Load getLoadById(UUID loadId, ToolContext toolContext) {

        UUID userId = (UUID)toolContext.getContext()
                .get(AIContextKeys.USER_ID);
        UserRoles role = (UserRoles) toolContext
                .getContext()
                .get(AIContextKeys.ROLE);
        Load load = loadService.getLoadById(loadId);

        validateLoadAccess(load, userId, role);
        return load;
    }


    @Tool(description = """ 
                Search for loads using shipper ID and load status.
                Use this when the user wants to find or list multiple loads.
                shipperId and status can be used as filters.
                page starts from 0.
                """)
    public List<Load> searchLoads(UUID shipperId, LoadStatus status, int page, int size) {
        return loadService.getLoads(shipperId, status, page, size);
    }


//    @Tool(description = "Cancel a load using its load ID")
//    public String cancelLoad(UUID loadId) {
//        loadService.cancelLoad(loadId);
//        return "Load " + loadId + " has been cancelled successfully.";
//    }


    @Tool(
            description = """
        Create a new load in the TMS.
        Required information:
        shipper ID, loading city, unloading city, loading date,
        product type, weight, weight unit, truck type and number of trucks.
        The load status is automatically set to POSTED by the TMS.
        Do not provide or generate load ID, status, datePosted or version.
        """
    )
    public Load createLoad(CreateLoadRequest request) {

        Load load = Load.builder()
                .shipperId(request.getShipperId())
                .loadingCity(request.getLoadingCity())
                .unloadingCity(request.getUnloadingCity())
                .loadingDate(request.getLoadingDate())
                .productType(request.getProductType())
                .weight(request.getWeight())
                .weightUnit(request.getWeightUnit())
                .truckType(request.getTruckType())
                .noOfTrucks(request.getNoOfTrucks())
                .build();

        return loadService.createLoad(load);
    }

    private void validateLoadAccess(
            Load load,
            UUID userId,
            UserRoles role) {

        if (role == UserRoles.SHIPPER) {

            if (!load.getShipperId().equals(userId)) {
                throw new AccessDeniedException(
                        "You are not authorized to access this load."
                );
            }

            return;
        }

        if (role == UserRoles.TRANSPORTER) {

            // For now transporter access can be handled
            // according to your eligibility/business rules.

            return;
        }

        throw new AccessDeniedException(
                "Unsupported user role."
        );
    }
}