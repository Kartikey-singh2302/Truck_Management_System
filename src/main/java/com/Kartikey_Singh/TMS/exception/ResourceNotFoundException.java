package com.Kartikey_Singh.TMS.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {

       super(message);

    }
}
