<?php

//ENTER YOUR DATABASE CONNECTION INFO BELOW:
$hostname="localhost";
$database="jedgar_workfriends";
$username="jedgar";
$password="abuelitos";


$db = new mysqli($hostname, $username, $password, $database);

if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}


function pull_data_json($sql){
	global $db;

	if(!$result = $db->query($sql)){
    
    die('There was an error running the query [' . $db->error . ']');
	
	}else{

		$results_array = array();
		while ($row = $result->fetch_assoc()) {
	  			$results_array[] = $row;
		}


		// check for empty result
		if (sizeof($result) > 0) {  //if we have a non-empty result
		    return json_encode($response);
		} else {
		    // no users found
		    $response["success"] = 0;
		    $response["message"] = "No users found";
		 
		    // echo no users JSON
		    return json_encode($response);
		}

	}
	
} 

?>