package com.personal.mmillstein.androidcalculator;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public class CalculatorUtils {
    private static final String ERROR_STRING = "Err!";


    private Optional<String> firstNumberStr;
    private Optional<String> secondNumberStr;
    private String calculatorStr;

    private boolean hasError;

    private Optional<Operation> operation;

    public CalculatorUtils() {
        this.calculatorStr = "";
        this.firstNumberStr = Optional.absent();
        this.secondNumberStr = Optional.absent();
        this.operation = Optional.absent();
    }

    public enum Operation {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("*"),
        DIVISION("/");

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        public static Operation from(String str) {
            for (Operation op : Operation.values()) {
                if (op.getSymbol().equals(str)) {
                    return op;
                }
            }
            throw new EnumConstantNotPresentException(Operation.class, str);
        }

        public String apply(float num1, float num2) {
            switch (this) {
                case ADDITION:
                    return String.valueOf(num1 + num2);
                case SUBTRACTION:
                    return String.valueOf(num1 - num2);
                case MULTIPLICATION:
                    return String.valueOf(num1 * num2);
                case DIVISION:
                    return String.valueOf(num1 / num2);
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public String getSymbol() {
            return this.symbol;
        }
    }


    public String addDigit(final String digit) {
        if (hasError) {
            return calculatorStr;
        }
        Function<String, String> applyDigit = new Function<String, String>() {
            @Override
            public String apply(String input) {
                input += digit;
                try {
                    Float.valueOf(input);
                    calculatorStr += digit;
                    return input;
                } catch (NumberFormatException e) {
                    hasError = true;
                    calculatorStr = ERROR_STRING;
                    return "";
                }
            }
        };

        if (!operation.isPresent()) {
            if (firstNumberStr.isPresent()) {
                firstNumberStr = firstNumberStr.transform(applyDigit);
            } else {
                firstNumberStr = Optional.of(digit);
                calculatorStr += digit;
            }
            return calculatorStr;
        } else {
            if (secondNumberStr.isPresent()) {
                secondNumberStr = secondNumberStr.transform(applyDigit);
            } else {
                secondNumberStr = Optional.of(digit);
                calculatorStr += digit;
            }
            return calculatorStr;
        }
    }

    public String addOperation(Operation op) {
        if (hasError) {
            return calculatorStr;
        }
        if (!firstNumberStr.isPresent()) {
            hasError = true;
            return ERROR_STRING;
        }
        if (operation.isPresent()) {
            calculate();
        }
        operation = Optional.of(op);
        return calculatorStr += op.getSymbol();
    }

    public String calculate() {
        if (!firstNumberStr.isPresent() || !secondNumberStr.isPresent()) {
            hasError = true;
            return ERROR_STRING;
        }
        float num1 = Float.valueOf(firstNumberStr.get());
        float num2 = Float.valueOf(secondNumberStr.get());
        calculatorStr = operation.get().apply(num1, num2);
        firstNumberStr = Optional.of(calculatorStr);
        secondNumberStr = Optional.absent();
        operation = Optional.absent();
        return calculatorStr;
    }
}
