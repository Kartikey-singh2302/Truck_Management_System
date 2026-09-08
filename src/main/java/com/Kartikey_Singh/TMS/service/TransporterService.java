package com.Kartikey_Singh.TMS.service;

import com.Kartikey_Singh.TMS.entity.Transporter;

import java.util.UUID;

public interface TransporterService {
    Transporter registerTransporter(Transporter transporter);
    Transporter getTransporterById(UUID transporterId);
    Transporter updateTrucks(UUID transporterId, String truckType, int count);
}
