package com.project.manager.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTest {
    @Test
    public void testIsValidEmailAddress() {
        boolean result = Validator.isEmailValid("EMAIL");
        boolean result2 = Validator.isEmailValid("EMAIL@");
        boolean result3 = Validator.isEmailValid("emailmail.pl");
        boolean result4 = Validator.isEmailValid("EMAIL@.pl");
        boolean result5 = Validator.isEmailValid("EMAIL@mail");

        Assert.assertFalse(result);
        Assert.assertFalse(result2);
        Assert.assertFalse(result3);
        Assert.assertFalse(result4);
        Assert.assertFalse(result5);

        boolean result6 = Validator.isEmailValid("EMAIL@mail.pl");

        Assert.assertTrue(result6);
    }
}
