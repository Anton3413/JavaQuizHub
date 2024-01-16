-- Вставка для первого вопроса и получение сгенерированного ID
WITH inserted_test1 AS (
    INSERT INTO test (book_id, category, question, explanation)
        VALUES (
                   1,
                   'JAVA_CORE',
                   'Given:\n 2. public class Bunnies {\n 3. static int count = 0;\n 4. Bunnies() {\n 5. while(count < 10) new Bunnies(++count);\n 6. }\n 7. Bunnies(int x) { super(); }\n 8. public static void main(String[] args) {\n 9. new Bunnies();\n10. new Bunnies(count);\n11. System.out.println(count++);\n12. }\n13. }',
                   ' B is correct. It’s legal to invoke "new" from within a constructor, and it’s legal to call super() on a class with no explicit superclass. On the real exam, it’s important to watch out for pre- and post-incrementing.  A, C, D, E, and F are incorrect based on the above.'
               )
        RETURNING id
)
-- Вставка для первого вопроса в таблицу answer, используя сгенерированный ID
INSERT INTO answer (is_correct, option_letter, question_id, answer_text)
VALUES
    (false, 'A', (SELECT id FROM inserted_test1), '9'),
    (true, 'B', (SELECT id FROM inserted_test1), '10'),
    (false, 'C', (SELECT id FROM inserted_test1), '11'),
    (false, 'D', (SELECT id FROM inserted_test1), '12'),
    (false, 'E', (SELECT id FROM inserted_test1), 'Compilation fails.'),
    (false, 'F', (SELECT id FROM inserted_test1), 'An exception is thrown at runtime.');

-- Вставка для второго вопроса и получение сгенерированного ID
WITH inserted_test2 AS (
    INSERT INTO test (book_id, category, question, explanation)
        VALUES (
                   1,
                   'JAVA_CORE',
                   'Given:\n 2. public class Jail {\n 3. private int x = 4;\n 4. public static void main(String[] args) {\n 5. protected int x = 6;\n 6. new Jail().new Cell().slam();\n 7. }\n 8. class Cell {\n 9. void slam() { System.out.println("throw away key " + x); }\n10. }\n11. }\n\n Which are true? (Choose all that apply.',
                   ' D is correct. Line 5 is declaring local variable "x", and local variables cannot have access modifiers. If line 5 read "int x = 6", the code would compile and the result would be "throw away key 4". Line 5 creates an anonymous Jail object, an anonymous Cell object, and invokes slam(). Inner classes have access to their enclosing class’s private variables.  A, B, C, E, and F are incorrect based on the above.'
               )
        RETURNING id
)
-- Вставка для второго вопроса в таблицу answer, используя сгенерированный ID
INSERT INTO answer (is_correct, option_letter, question_id, answer_text)
VALUES
    (false, 'A', (SELECT id FROM inserted_test2), 'Compilation succeeds.'),
    (false, 'B', (SELECT id FROM inserted_test2), 'The output is "throw away key 4".'),
    (false, 'C', (SELECT id FROM inserted_test2), 'The output is "throw away key 6".'),
    (true, 'D', (SELECT id FROM inserted_test2), 'Compilation fails due to an error on line 5.'),
    (false, 'E', (SELECT id FROM inserted_test2), 'Compilation fails due to an error on line 6.'),
    (false, 'F', (SELECT id FROM inserted_test2), 'Compilation fails due to an error on line 9.');
