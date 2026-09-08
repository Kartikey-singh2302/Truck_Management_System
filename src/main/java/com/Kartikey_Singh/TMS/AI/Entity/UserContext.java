package com.Kartikey_Singh.TMS.AI.Entity;

import com.Kartikey_Singh.TMS.entity.enums.UserRoles;

import java.util.UUID;

public record UserContext(UUID userId, UserRoles role) {

}
