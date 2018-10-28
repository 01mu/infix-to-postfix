/*
 * infix-to-postfix
 * github.com/01mu/infix-to-postfix
 */

public interface Stack<AnyType>
{
  int size();
  boolean isEmpty();
  void push(AnyType newValue);
  AnyType top();
  AnyType pop();
}
