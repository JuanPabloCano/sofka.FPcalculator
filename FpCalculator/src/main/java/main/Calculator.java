package main;

import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class Calculator {
    public static void main(String[] args) {

        int add = addition.applyAsInt(2, 5);
        int sub = subtraction.applyAsInt(5, 2);
        int mul = multiply.applyAsInt(10, 7);
        int div = division.apply(10, 2);

        System.out.println("Suma: " + add);
        System.out.println("Resta: " + sub);
        System.out.println("Multiplicación: " + mul);
        System.out.println("División: " + div);

    }

    // Suma
    static IntBinaryOperator addition = Integer::sum;  // Referencia a método sum de java.lang.Integer

    // Resta
    static IntBinaryOperator subtraction = (num1, num2) -> num1 - num2;

    // Multiplicación
    static IntBinaryOperator multiply = (num1, num2) ->
            IntStream.range(0, num2 + 1).reduce((collector, number) ->
                    addition.applyAsInt(collector, num1)).orElseThrow();


    /* Se usa IntBinaryOperator de la clase Functional, esta interfaz funcional nos permite realizar operaciones
       entre dos números enteros mediante una expresión Lambda, dando como resultado un número del mismo tipo. Es más
       específica para este tipo de operaciones que la interfaz funcional BiFunction */


    // División
    static BiFunction<Integer, Integer, Integer> division = (num1, num2) -> {
        if (num1.equals(0) && num2.equals(0)) {
            throw new IllegalArgumentException("Error, ambos valores no pueden ser igual a cero");
        }
        if (num2.equals(0)) {
            throw new IllegalArgumentException("Error, no se puede hacer una división por cero");
        }
        return IntStream.range(0, num1)
                .reduce((collector, number) ->
                        multiply.applyAsInt(number, num2) <= num1 ? addition.applyAsInt(collector, 1)
                                : collector).orElse(0);
    };

    /* La división se hace con la interfaz BiFunction para tener la posibilidad de evaluar las excepciones con
    equals, se podría haber hecho con IntBinaryOperator, pero esta interfaz no maneja esos métodos. Se utiliza
    IntStream que es la interfaz de enteros a diferencia de stream() y devuelve valores enteros, reduce con dos
    parámetros y una función lambda que hace el llamado de la función multiply que recibe dos parámetros y los evalúa
     con un condicional ternario para que los números no sean 0 y arrojen un error */
}
