# infix-to-postfix
Convert infix expressions to postfix expressions using elementary data structures.
## Input (`input`):
```
1+ 2
6+2/3*(5+2)
x+4
```
## Output:
```
Infix:   1+2
Postfix: 1 2 + 
Result:  3.0

Infix:   6+2/3*(5+2)
Postfix: 6 2 3 / 5 2 + * + 
Result:  10.666666666666666

Infix:   x+4
Postfix: x 4 + 
Enter a value for 'x': 
99
Result:  103.0

```
## Build
```
./build
```
