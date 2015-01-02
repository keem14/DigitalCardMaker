<?php

require("config.php");

if (!empty($_POST)) {
  
    $query = " 
            SELECT 
                user_id, 
                email, 
                password
            FROM users 
            WHERE 
                email = :email 
        ";
    
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
    

    $validated_info = false;
    
    
    $row = $stmt->fetch();
    if ($row) {

        if ($_POST['password'] === $row['password']) {
            $login_ok = true;
        }
    }
    

    if ($login_ok) {
        $response["success"] = 1;
        $response["message"] = "Login successful!";
        $response["user"] = $row['user_id'];
        $_SESSION['user_id'] =  $row['user_id'];

        die(json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    }
}
?>	