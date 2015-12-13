package com.personal.mmillstein.androidcalculator;

import junit.framework.TestCase;

public class CalculatorUtilsTest extends TestCase {


    public void testAddDigit() throws Exception {
        CalculatorUtils calc = new CalculatorUtils();
        assertEquals(calc.addDigit("1"), "1");
        assertEquals(calc.addDigit("2"), "12");
        assertEquals(calc.addDigit("."), "12.");
        assertEquals(calc.addDigit("3"), "12.3");
        assertEquals(calc.addDigit("."), "Err!");
    }

    public void testAddition() throws Exception {
        CalculatorUtils calc = new CalculatorUtils();
        calc.addDigit("1");
        calc.addOperation(CalculatorUtils.Operation.ADDITION);
        calc.addDigit("9");
        calc.addDigit("9");
        assertEquals(calc.calculate(), "100.0");
    }

    public void testSubtraction() throws Exception {
        CalculatorUtils calc = new CalculatorUtils();
        calc.addDigit("9");
        calc.addDigit("9");
        calc.addOperation(CalculatorUtils.Operation.SUBTRACTION);
        calc.addDigit("1");
        assertEquals(calc.calculate(), "98.0");
    }

    public void testSubtractionToNegative() throws Exception {
        CalculatorUtils calc = new CalculatorUtils();
        calc.addDigit("9");
        calc.addOperation(CalculatorUtils.Operation.SUBTRACTION);
        calc.addDigit("1");
        calc.addDigit("0");
        assertEquals(calc.calculate(), "-1.0");
    }

    public void testMultiplication() throws Exception {
        CalculatorUtils calc = new CalculatorUtils();
        calc.addDigit("9");
        calc.addOperation(CalculatorUtils.Operation.MULTIPLICATION);
        calc.addDigit("1");
        calc.addDigit("0");
        assertEquals(calc.calculate(), "90.0");
    }

    public void testDivision() throws Exception {
        CalculatorUtils calc = new CalculatorUtils();
        calc.addDigit("1");
        calc.addDigit("0");
        calc.addDigit("0");
        calc.addOperation(CalculatorUtils.Operation.DIVISION);
        calc.addDigit("1");
        calc.addDigit("0");
        assertEquals(calc.calculate(), "10.0");
    }

    public void testDivisionDecimal() throws Exception {
        CalculatorUtils calc = new CalculatorUtils();
        calc.addDigit("1");
        calc.addOperation(CalculatorUtils.Operation.DIVISION);
        calc.addDigit("3");
        assertEquals(calc.calculate(), "0.33333334");
    }
}