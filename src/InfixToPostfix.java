/*
 * infix-to-postfix
 * github.com/01mu/infix-to-postfix
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Iterator;

public class InfixToPostfix
{
    public static void main(String[] args) throws FileNotFoundException
    {
        DynamicArrayStack<BinaryTree<String>> treeStack =
            new DynamicArrayStack<BinaryTree<String>>(1);

        LinkedQueue<String> inputQueue = new LinkedQueue<String>();

        FileInputStream fstream = new FileInputStream("../data/input");

        Scanner input = new Scanner(fstream);
        Scanner operandInput = new Scanner(System.in);

        while(input.hasNextLine()) {
            convertToPostfix(inputQueue, input);
            createBinaryTree(treeStack, inputQueue);
            evaluateExpression(treeStack, operandInput);
        }

        operandInput.close();
        input.close();
    }

    public static void convertToPostfix(LinkedQueue<String> inputQueue,
        Scanner input)
    {
        DynamicArrayStack<String> stack = new DynamicArrayStack<String>(1);
        String operandString = "";

        String line = input.nextLine();
        line = line.replaceAll("\\s+", "");

        System.out.println("Infix:   " + line);

        for(int i = 0; i < line.length(); i++) {
            String character = Character.toString(line.charAt(i));

            if(!isNumeric(character) && !isAlpha(character)) {
                if(!operandString.equals("")) {
                    inputQueue.enqueue(operandString);
                }

                operandString = "";

                checkInputs(stack, character, inputQueue);

                if(!character.equals(")")) {
                    stack.push(character);
                }
            } else {
                operandString = operandString + character;
            }
        }

        if(!operandString.equals("")) {
            inputQueue.enqueue(operandString);
        }

        operandString = "";

        while(!stack.isEmpty()) {
            inputQueue.enqueue(stack.pop());
        }
    }

    public static void checkInputs(DynamicArrayStack<String> stack, String ctr,
        LinkedQueue<String> inputQueue)
    {
        if(!stack.isEmpty()) {
            if((ctr.equals("*") || ctr.equals("/") ) &&
                (stack.top().equals("*") ||
                    stack.top().equals("/"))) {
                while(stack.top().equals("*") || stack.top().equals("/")) {
                    inputQueue.enqueue(stack.pop());

                    if(stack.isEmpty()) {
                        break;
                    }
                }
            } else if((ctr.equals("-") || ctr.equals("+") ) &&
                (stack.top().equals("+") || stack.top().equals("-") ||
                    stack.top().equals("/") ||
                        stack.top().equals("*"))) {
                while(stack.top().equals("*") || stack.top().equals("/") ||
                    stack.top().equals("-") || stack.top().equals("+")) {
                    inputQueue.enqueue(stack.pop());

                    if(stack.isEmpty()) {
                        break;
                    }
                }
            } else if(ctr.equals(")")) {
                while(true) {
                    if(stack.top().equals("(")) {
                        stack.pop();

                        break;
                    } else {
                        inputQueue.enqueue(stack.pop());
                    }

                    if(stack.isEmpty()) {
                        break;
                    }
                }
            }
        }
    }

    public static void createBinaryTree(
        DynamicArrayStack<BinaryTree<String>> treeStack,
            LinkedQueue<String> inputQueue)
    {
        System.out.print("Postfix: ");

        while(!inputQueue.isEmpty()) {
            String character = inputQueue.first();

            System.out.print(inputQueue.first() + " ");

            inputQueue.dequeue();

            if(isNumeric(character) || isAlpha(character)) {
                BinaryTree<String> operandNode =
                    new BinaryTree<String>(character);

                treeStack.push(operandNode);
            } else {
                BinaryTree<String> operatorNode = new BinaryTree<>(character);
                BinaryTree<String> rightChildOperand = treeStack.pop();
                BinaryTree<String> leftChildOperand = treeStack.pop();

                operatorNode.attach(leftChildOperand, rightChildOperand);

                treeStack.push(operatorNode);
            }
        }

        System.out.print("\n");
    }

    public static void evaluateExpression(
        DynamicArrayStack<BinaryTree<String>> treeStack, Scanner operandInput)
    {
        DynamicArrayStack<Double> operandStack =
            new DynamicArrayStack<Double>(1);

        BinaryTree<String> finalTree = treeStack.pop();
        Iterator<String> treeIterator = finalTree.iterator();

        while(treeIterator.hasNext()) {
            String character = treeIterator.next();

            if(isNumeric(character)) {
                Double operand = Double.valueOf(character);
                operandStack.push(operand);
            } else if(isAlpha(character)) {
                System.out.println("Enter a value for '" + character + "': ");
                double varToOperand = operandInput.nextDouble();

                operandStack.push(varToOperand);
            } else {
                double rightOperand = operandStack.pop();
                double leftOperand = operandStack.pop();

                if(character.equals("+")) {
                    operandStack.push(leftOperand + rightOperand);
                } else if(character.equals("-")) {
                    operandStack.push(leftOperand - rightOperand);
                } else if(character.equals("*")) {
                    operandStack.push(leftOperand * rightOperand);
                } else if(character.equals("/")) {
                    operandStack.push(leftOperand / rightOperand);
                }
            }
        }

        System.out.print("Result:  " + operandStack.pop() + "\n\n");
    }

    public static boolean isNumeric(String string)
    {
        return string.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isAlpha(String string)
    {
        return string.matches("[a-zA-Z]+");
    }
}
