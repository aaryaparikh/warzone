# **Coding Convention**

## **Coding Layout**

**Format of Block (for / if / switch / class)**
```
if(some_condition){
    statement 1
    statement 2
}
```

```
for(iterator){
    statement 1
    statement 2
}
```

```
public Animal{
    Attribute 1
    Attribute 2

    Animal(){
        statements
    }

    behavior(int p1, int p2){
        statements
    }
} 
```
**Blank Spaces to separate code components/sections**
1. In the major section of long and complicated function.
2. In between public, protected, and private sections of a class declaration.
3. In between class declarations in a file.
4. In between function and method definitions.

**Indentation**
1. We use indentation of 4 columns.
2. Body of the function is indented with respect to its header.
3. Body of loop and conditional statements are indented with respect to their first line.

**Space pad operators and equals**
 ```
    int add = a + b + c;

    if(x == 5)

    for(int i = 0; i < n; i++)

    while( x != n)
``` 

**Comments**

`//`      - Single Line Comment

`/* */`   - Multi Line Comment  

`/** */`  - Java Docs 

## **Naming Convention**

- class names in CamelCase that starts with a capital letter

- data members start with d_ 

- method parameters start with p_

- local variables start with l_

- global variables in capital letters

- static members start with a capital letter, non-static members start with a lower case letter

## Project Structure

- one folder for every module in the high-level design
- tests are is a separate folder that has the exact same structure as the code folder
- 1-1 relationship between tested classes and test classes.

### References 

1. Build 1 -- Provided by Dr. Amin Ranj Bar

2. Coding Convention -- Provided by Dr. Amin Ranj Bar in lecture pdf