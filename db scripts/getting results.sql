select  qr.questionid ,qr.ANSWERID, q.questiontext, qa.answertext, qa.iscorrect, qca.answertext as correctanswer
from quizresult qr
join question q on qr.QUESTIONID = q.QUESTIONID
join questionanswer qa on qr.QUESTIONID = qa.QUESTIONID and qr.ANSWERID = qa.ANSWERID
join (select answertext, questionid from questionanswer where iscorrect = true) qca
on qa.QUESTIONID = qca.questionid
where userid = 1 and quizid = 100


select * from quizresult
