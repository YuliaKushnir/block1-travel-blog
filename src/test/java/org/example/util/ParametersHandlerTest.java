package org.example.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ParametersHandlerTest {

    private final ParametersValidator validator = new ParametersValidator();

    @Test
    void testHasRequiredArgsCountWhenPassedTwoArgumentsThenReturnTrue() {
        String[] args = {"./data", "category"};
        assertTrue(validator.hasRequiredArgsCount(args));
    }

    @Test
    void testHasRequiredArgsCountWhenPassedOneArgumentThenReturnFalse() {
        String[] args = {"./data"};
        assertFalse(validator.hasRequiredArgsCount(args));
    }

    @Test
    void testIsAttributeSupportedWhenAttributeIsAllowedThenReturnTrue() {
        assertTrue(validator.isAttributeSupported("category"));
    }

    @Test
    void testIsAttributeSupportedWhenAttributeIsUnsupportedThenReturnFalse() {
        assertFalse(validator.isAttributeSupported("test"));
    }

    @Test
    void testCheckIsDirectoryWhenPassedValidDirectoryThenReturnTrue() {
        File folder = new File(System.getProperty("java.io.tmpdir"));
        assertTrue(validator.checkIsDirectory(folder));
    }

    @Test
    void testCheckIsDirectoryWhenPassedInvalidFileThenReturnFalse() {
        File folder = new File("file.txt");
        assertFalse(validator.checkIsDirectory(folder));
    }
}