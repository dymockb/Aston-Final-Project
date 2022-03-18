INSERT INTO TypeOfShow
(
    ID, 
    Name,
    LiveMusic
) VALUES 
(1, 'Theatre', 0),
(2, 'Musical', 0),
(3, 'Musical with live music', 1),
(4, 'Concert', 1),
(5, 'Opera', 0),
(6, 'Opera with live music', 1);


INSERT INTO ShowDetail
(
    ID,
    TYPEID,
    Name,
    Description,
    Duration,
    EnglishLanguage
) VALUES 
(1,1,'Romeo and Juliet','Theatre show',120,1),
(2,2,'Beauty and the Beast','Musical show',150,1),
(3,3,'Mamma Mia!','ABBA + Musical show',110,1),
(4,4,'Andrea Bocelli','Andrea Bocelli + Concert',100,null);END;

