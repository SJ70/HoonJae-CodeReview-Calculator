package src.main;

import src.main.exception.BadExpressionException;

import java.util.*;

public class Calculator {

    public long calculate(String expression) throws BadExpressionException {
        StringTokenizer stringTokenizer = new StringTokenizer(expression);

        if(stringTokenizer.countTokens()%2==0){
            throw new BadExpressionException();
        }

        long resultValue = 0;

        long currentValue = getLongValue(stringTokenizer.nextToken());
        char currentOperator = '+';

        while(stringTokenizer.hasMoreTokens()){
            char nextOperator = getOperator(stringTokenizer.nextToken());
            long nextValue = getLongValue(stringTokenizer.nextToken());

//            System.out.printf("%s%d  |  %d %s %d %s %d\n",nextOperator,nextValue,resultValue,currentOperator,currentValue,nextOperator,nextValue);

            if(nextOperator == '+' || nextOperator == '-'){
                resultValue = calculateByOperator(resultValue, currentValue, currentOperator);  // 덧셈이나 뺄셈만 수행
                currentValue = nextValue;
                currentOperator = nextOperator;
            }
            else if(nextOperator == '*' || nextOperator == '/'){
                currentValue = calculateByOperator(currentValue, nextValue, nextOperator);
            }
        }
        resultValue = calculateByOperator(resultValue, currentValue, currentOperator);  // 덧셈이나 뺄셈만 수행

        return resultValue;
    }

    public long calculateByOperator(long value1, long value2, char Operator) throws BadExpressionException {
        switch(Operator){
            case '+' : return value1 + value2;
            case '-' : return value1 - value2;
            case '*' : return value1 * value2;
            case '/' :
                if(value2==0){
                    throw new BadExpressionException("0으로는 나눌 수 없습니다");
                }
                return value1 / value2;
            default: throw new BadExpressionException();
        }
    }

    public char getOperator(String string) {
        char character = string.charAt(0);
        if(string.length()!=1 || (character!='+' && character!='-' && character!='*' && character!='/')) {
            throw new BadExpressionException("올바른 부호를 입력해주세요");
        }
        return character;
    }

    public long getLongValue(String string) {
        try{
            return Long.parseLong(string);
        }
        catch(NumberFormatException e){
            throw new BadExpressionException("올바른 숫자를 입력해주세요");
        }
    }

}
