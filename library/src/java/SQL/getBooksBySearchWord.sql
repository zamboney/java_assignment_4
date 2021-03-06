SELECT COUNT(BOOKS_GENRES.ID) as COPIES, BOOKS_GENRES."NAME" as  "NAME" ,BOOKS_GENRES.AUTHOR as  AUTHOR ,CONDITIONS."NAME" as "CONDITION",CONDITIONS."ID" as "CONDITION_ID", BOOKS_GENRES.GENRE_NAME AS GENRE_NAME
FROM (SELECT APP.BOOKS.ID as ID, AUTHOR as AUTHOR, APP.BOOKS."NAME" as "NAME", APP.GENRES."NAME" as GENRE_NAME, APP.BOOKS.CONDITION_ID as  CONDITION_ID, APP.BOOKS.GENRE_ID as GENRE_ID  FROM APP.BOOKS,APP.GENRES WHERE APP.BOOKS.CONDITION_ID = APP.GENRES.ID) AS BOOKS_GENRES, APP.CONDITIONS
WHERE BOOKS_GENRES.CONDITION_ID = APP.CONDITIONS.ID 
AND LOWER(BOOKS_GENRES."NAME") LIKE '%%%s%%'
AND BOOKS_GENRES.ID NOT IN(SELECT BOOK_ID FROM APP.RENTS AS RENTS Where BOOKS_GENRES.ID = RENTS.BOOK_ID AND RENTS.END_DATE IS NULL)AND CONDITIONS."NAME"  != 'NOT IN USE'
GROUP BY BOOKS_GENRES."NAME" , BOOKS_GENRES."GENRE_NAME" , BOOKS_GENRES.AUTHOR, CONDITIONS."NAME", CONDITIONS."ID"