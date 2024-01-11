
CREATE TABLE book (
                      id serial PRIMARY KEY,
                      author VARCHAR(30),
                      title VARCHAR(255)
);

CREATE TABLE test (
                      id SERIAL PRIMARY KEY,
                      book_id INTEGER REFERENCES book,
                      category VARCHAR(255) CHECK (category IN ('JAVA_CORE', 'STREAMS', 'MULTITHREADING', 'INHERITANCE', 'ALGORITHMS')),
                      question VARCHAR(255)
);

CREATE TABLE answer (
                        id SERIAL PRIMARY KEY,
                        is_correct BOOLEAN,
                        option_letter CHAR,
                        question_id INTEGER REFERENCES test,
                        answer_text VARCHAR(255)
);