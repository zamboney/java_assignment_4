SELECT 
    USER_NAME as "User Name" ,FNAME || ' ' || LNAME as "Full Name" , APP.USERS.ID,
    APP.USERS.PENALTY as Penalty , 
    APP.USERS.PREMISSION_ID as PREMISSION_ID,
    APP.PERMISSIONS."NAME" as Permission ,
    APP.USERS.PREMISSION_ID as PREMISSION_ID 
FROM APP.USERS, APP.PERMISSIONS 
