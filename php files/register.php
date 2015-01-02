<?php


require("config.php");


if (!empty($_POST)) {

    if (empty($_POST['email']) || empty($_POST['password'])) {
        

        $response["success"] = 0;
        $response["message"] = "Please Enter Both a email and Password.";
        

        die(json_encode($response));
    }
    

    $query        = " SELECT 1 FROM users WHERE email = :email";

    $query_params = array(':email' => $_POST['email']);
    
    try {

        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {

        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
    }
    

    $row = $stmt->fetch();
    if ($row) {

        $response["success"] = 0;
        $response["message"] = "I'm sorry, this email is already in use";
        die(json_encode($response));
    }
    

    $query = "INSERT INTO users ( email, password ) VALUES ( :email, :pass ) ";
    

    $query_params = array(
        ':email' => $_POST['email'],
        ':pass' => $_POST['password']
    );
    
  
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {

        $response["success"] = 0;
        $response["message"] = "Database Error2. Please Try Again!";
        die(json_encode($response));
    }
    


    $response["success"] = 1;
    $response["message"] = "User Successfully Added!";
    echo json_encode($response); 
} 
?>
