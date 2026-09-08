package com.Kartikey_Singh.TMS.service;

import com.Kartikey_Singh.TMS.entity.Load;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;

import java.util.List;
import java.util.UUID;

public interface LoadService {
    Load createLoad(Load load);
    List<Load> getLoads(UUID shipperId, LoadStatus status, int page, int size);
    Load getLoadById(UUID loadId);
    void cancelLoad(UUID loadId);
}
