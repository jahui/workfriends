<?php
 
/*
 * Following code will list all Users
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once dirname(dirname(__FILE__)) . '/workfriends/db_connect.php';

 
// get all users from user table, or kill the link if the query has problems
$query = "SELECT * FROM user";

//call the pull_data function from the db_functions.php file
$result = pull_data_json($query);
 

// check for empty result
if (sizeof($result) > 0) {  //if we have a non-empty result
    echo $result;
    
} else {
    // no users found
    $response["success"] = 0;
    $response["message"] = "No users found";
 
    // echo no users JSON
    echo json_encode($response);
}

?>




