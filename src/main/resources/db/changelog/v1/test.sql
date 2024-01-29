UPDATE test
SET question = '2. public class Jail {\n
3.     private int x = 4;\n
4.     public static void main(String[] args) {\n
5.         protected int x = 6;\n
6.         new Jail().new Cell().slam();\n
7.     }\n
8.     class Cell {\n
9.         void slam() { System.out.println("throw away key " + x); }\n
10.     }\n
11. }
'
WHERE id=7;