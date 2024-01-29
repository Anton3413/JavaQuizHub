UPDATE test
SET question = ' 2. public class Bunnies {\n
 3.     static int count = 0;\n
 4.     Bunnies() {\n
 5.         while(count < 10) new Bunnies(++count);\n
 6.     }\n
 7.     Bunnies(int x) { super(); }\n
 8.     public static void main(String[] args) {\n
 9.         new Bunnies();\n
10.         new Bunnies(count);\n
11.         System.out.println(count++);\n
12.     }\n
13. }
'
WHERE id=6;