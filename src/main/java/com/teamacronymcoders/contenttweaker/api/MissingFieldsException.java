package com.teamacronymcoders.contenttweaker.api;

import java.util.List;

public class MissingFieldsException extends RuntimeException {
    public MissingFieldsException(String name, List<String> missingFields) {
        super("Content " + name + " is missing fields " + missingFields.toString());
    }
}
