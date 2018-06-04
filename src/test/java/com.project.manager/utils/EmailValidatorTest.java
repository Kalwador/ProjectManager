package com.project.manager.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTest {
    @Test
    public void testIsValidEmailAddress() {
        boolean result = EmailValidator.isEmailValid("EMAIL");
        boolean result2 = EmailValidator.isEmailValid("EMAIL@");
        boolean result3 = EmailValidator.isEmailValid("emailmail.pl");
        boolean result4 = EmailValidator.isEmailValid("EMAIL@.pl");
        boolean result5 = EmailValidator.isEmailValid("EMAIL@mail");

        Assert.assertFalse(result);
        Assert.assertFalse(result2);
        Assert.assertFalse(result3);
        Assert.assertFalse(result4);
        Assert.assertFalse(result5);

        boolean result6 = EmailValidator.isEmailValid("EMAIL@mail.pl");

        Assert.assertTrue(result6);
    }
}
