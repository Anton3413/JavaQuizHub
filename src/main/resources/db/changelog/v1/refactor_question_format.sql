UPDATE test
SET question ='Given:
2. public class Bunnies {
3.     static int count = 0;
4.     Bunnies() {
5.         while(count < 10) new Bunnies(++count);
6.     }
7.     Bunnies(int x) { super(); }
8.     public static void main(String[] args) {
9.         new Bunnies();
10.        new Bunnies(count);
11.        System.out.println(count++);
12.    }
13. }',
    clarifying_question = 'What is the result?'
WHERE id = 6;

UPDATE test
SET question ='Given:
2. public class Jail {
3.     private int x = 4;
4.     public static void main(String[] args) {
5.         protected int x = 6;
6.         new Jail().new Cell().slam();
7.     }
8.     class Cell {
9.         void slam() { System.out.println("throw away key " + x); }
10.    }
11. }',
    clarifying_question = 'Which are true? (Choose all that apply.)'
WHERE id = 7;


