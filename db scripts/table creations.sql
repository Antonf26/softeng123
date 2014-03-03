CREATE TABLE QUIZ
(QuizId integer not null generated always as identity (start with 100, increment by 1),
FeedbackAvailable boolean,
NavigationEnabled boolean,
RandomiseQuestions boolean,
TimeAllowed int,
TimeOutBehaviour int,
ShowPracticeQuestrion boolean,
PRIMARY KEY (QuizId));

CREATE TABLE QUESTION 
(
QuestionID int not null generated always as identity (start with 100, increment by 1),
QuestionText varchar(255),
QuestionAuthor int,
IsValidated boolean,
AuthorID int,
PRIMARY KEY(QuestionID),
CONSTRAINT QAUTHFK FOREIGN KEY(AuthorID)
REFERENCES APPUSERS(USERID));





CREATE TABLE QUIZQUESTION
(QuizId int,
QuestionId int,
PRIMARY KEY (QuizID, QuestionId),
CONSTRAINT QQFK FOREIGN KEY (QuizID)
REFERENCES QUIZ(QuizID),
CONSTRAINT QQFKK FOREIGN KEY (QuestionID)
REFERENCES QUESTION(QuestionID)
) ;

CREATE TABLE QUESTIONANSWER
(
QuestionID int,
AnswerID int,
AnswerText varchar(255),
IsCorrect boolean,
PRIMARY KEY (QuestionID, AnswerID, USERID),
CONSTRAINT QAFK FOREIGN KEY (QuestionID) 
REFERENCES Question (QuestionID)

);

drop table questionanswer

CREATE TABLE QUIZRESULT
(
QuizID int,
QuestionID int,
USERID int,
Correct boolean,
PRIMARY KEY(QuizID, QuestionID, USERID),
CONSTRAINT QRQFK FOREIGN KEY (QuizID)
REFERENCES QUIZ (QUIZID),
CONSTRAINT QRQUFK FOREIGN KEY(QuestionID)
REFERENCES QUESTION (QuestionID),
CONSTRAINT QRUFK FOREIGN KEY(USERID)
REFERENCES APPUSERS (USERID))

