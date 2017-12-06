package com.fredericboisguerin.insa.calculateurprix.ui.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class UserInputConverterTest {

    @Test
    void should_convert_amount_as_text_to_big_decimal() throws Exception {
        String someAmount = "123.456";
        UserInputConverter userInputConverter = new UserInputConverter();

        BigDecimal convertedAmount = userInputConverter.convertToBigDecimal(someAmount);

        assertThat(convertedAmount).isEqualTo(new BigDecimal(someAmount));
    }

    @Test
    void should_fail_if_amount_is_not_a_decimal_number() throws Exception {
        String someInvalidAmount = "abc";
        UserInputConverter userInputConverter = new UserInputConverter();

        assertThatThrownBy(() -> userInputConverter.convertToBigDecimal(someInvalidAmount)).isInstanceOf(InvalidAmount.class);
    }

    @Test
    void should_convert_quantity_as_text_to_integer() throws Exception {
        String someAmount = "123";
        UserInputConverter userInputConverter = new UserInputConverter();

        int convertedAmount = userInputConverter.convertToQuantity(someAmount);

        assertThat(convertedAmount).isEqualTo(123);
    }

    @Test
    void should_fail_if_quantity_is_not_a_decimal_number() throws Exception {
        String someInvalidQuantity = "123.456";
        UserInputConverter userInputConverter = new UserInputConverter();

        assertThatThrownBy(() -> userInputConverter.convertToQuantity(someInvalidQuantity)).isInstanceOf(InvalidQuantity.class);
    }
}