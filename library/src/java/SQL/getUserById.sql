SELECT APP.USERS.ID as ID,
       FNAME,
       LNAME,
       USER_NAME,
       PENALTY,
       APP.PERMISSIONS."NAME" as PERMISSION_NAME,
       APP.PERMISSIONS.ID as PERMISSION_ID
FROM APP.USERS,APP.PERMISSIONS 
WHERE  APP.USERS.PREMISSION_ID = APP.PERMISSIONS.ID 
AND APP.USERS.IS_DELETED = FALSE
And APP.USERS.ID = %s