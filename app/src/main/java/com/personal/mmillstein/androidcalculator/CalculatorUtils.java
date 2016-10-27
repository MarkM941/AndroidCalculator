package com.personal.mmillstein.androidcalculator;

import com.google.common.base.Optional;

public class CalculatorUtils {
    private static final String ERROR_STRING = "Err!";


    private Optional<Float> firstNumber;
    private Optional<Float> secondNumber;

    private boolean hasError;

    private Optional<Operation> operation;

    public CalculatorUtils() {
        this.firstNumber = Optional.absent();
        this.secondNumber = Optional.absent();
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

        public Float apply(float num1, float num2) {
            switch (this) {
                case ADDITION:
                    return num1 + num2;
                case SUBTRACTION:
                    return num1 - num2;
                case MULTIPLICATION:
                    return num1 * num2;
                case DIVISION:
                    return num1 / num2;
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
            return ERROR_STRING;
        }

        final Float newDigit = Float.valueOf(digit);

        if (!operation.isPresent()) {
            firstNumber = Optional.of(newDigit + firstNumber.or(0f) * 10);
        } else {
            secondNumber = Optional.of(newDigit + secondNumber.or(0f) * 10);
        }
        return getCalculatorText();
    }

    public String addOperation(Operation op) {
        if (hasError) {
            return ERROR_STRING;
        }
        if (!firstNumber.isPresent()) {
            hasError = true;
            return ERROR_STRING;
        }
        if (operation.isPresent()) {
            calculate();
            return addOperation(op);
        }
        operation = Optional.of(op);
        return getCalculatorText();
    }

    public String calculate() {
        if (!firstNumber.isPresent() || !secondNumber.isPresent()) {
            hasError = true;
            return ERROR_STRING;
        }

        float num1 = firstNumber.get();
        float num2 = secondNumber.get();
        float result = operation.get().apply(num1, num2);
        resetCalculatorToValue(result);
        return getCalculatorText();
    }

    private void resetCalculatorToValue(float result) {
        firstNumber = Optional.of(result);
        secondNumber = Optional.absent();
        operation = Optional.absent();
    }

    private String getCalculatorText() {
        String result = "";
        if (firstNumber.isPresent()) {
            result += firstNumber.get();
        }
        if (operation.isPresent()) {
            result += operation.get().getSymbol();
        }
        if (secondNumber.isPresent()) {
            result += secondNumber.get();
        }
        return result;
    }
}
