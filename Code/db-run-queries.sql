-- select show info
SELECT ShowName, ShowDescription, PerformanceDateTime FROM performances
JOIN shows ON shows.ID = performances.ShowID;

-- select all shows
SELECT * FROM performances
JOIN shows ON shows.ID = performances.ShowID;END;