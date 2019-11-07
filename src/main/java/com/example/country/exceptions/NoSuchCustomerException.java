package com.example.country.exceptions;

import java.util.NoSuchElementException;

public class NoSuchCustomerException extends NoSuchElementException {

    public NoSuchCustomerException(long id) {
        super("Country with countryId " + id + " not found");
    }
}
