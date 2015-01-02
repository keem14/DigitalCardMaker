<?php

//load and connect to MySQL database stuff
require("config.php");

if (!empty($_POST)) {
	//initial query
	$query = "INSERT INTO cards ( name, pos, company, address, phone, ext, fax, email, twitter, message, user ) 
                    VALUES ( :name, :pos, :company, :address, :phone, :ext, :fax, :email, :twitter, :message, :user ) ";

    //Update query
 $query_params = array(

        ':name' => $_POST['name'],
        ':pos' => $_POST['pos'],
	':company' => $_POST['company'],
        ':address' => $_POST['address'],
        ':phone' => $_POST['phone'],
        ':ext' => $_POST['ext'],
        ':fax' => $_POST['fax'],
        ':email' => $_POST['email'],
        ':twitter' => $_POST['twitter'],
        ':message' => $_POST['message'],
        ':user' => $_POST['user_id']
    );
  
	//execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't add card!";
        die(json_encode($response));
    }
    $response["lastID"] = $db->lastInsertId();
    $response["success"] = 1;
    $response["message"] = "card Successfully Added!";
    echo json_encode($response);
   
} 
?>