package com.kells.math;

import java.util.StringTokenizer;

public class MathStruct
{
    int operandOne;
    int operandTwo;
    String operator;
    StringTokenizer tokenizer;

    public MathStruct(String input)
    {
        tokenizer = new StringTokenizer(input);
        this.operandOne = Integer.parseInt(tokenizer.nextToken());
        this.operator = tokenizer.nextToken();
        this.operandTwo = Integer.parseInt(tokenizer.nextToken());
    }

    public int calculate()
    {
        return switch (operator)
                {
                    case "+" -> operandOne + operandTwo;
                    case "-" -> operandOne - operandTwo;
                    case "*" -> operandOne * operandTwo;
                    case "/" -> operandOne / operandTwo;
                    default -> 0;
                };
    }
}
