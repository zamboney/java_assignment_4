/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ritzhaki
 * Created: Feb 9, 2017
 */
SELECT 
    USER_NAME as "User Name" ,FNAME || ' ' || LNAME as "Full Name" , APP.USERS.ID,
    APP.USERS.PENALTY as Penalty , 
    APP.USERS.PREMISSION_ID as PREMISSION_ID,
    APP.PERMISSIONS."NAME" as Permission ,
    APP.USERS.PREMISSION_ID as PREMISSION_ID 
FROM APP.USERS, APP.PERMISSIONS 
