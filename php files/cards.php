<?php


require("config.php");


$query = "Select * FROM cards WHERE user = :user ";
$query_params = array(':user' => $_POST['u_id']);

try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}


$rows = $stmt->fetchAll();


if ($rows) {

    $response["posts"]   = array();
    
    foreach ($rows as $row) {
        $post             = array();
        
        $post["card_id"]  = $row["card_id"];
        $post["name"]  = $row["name"];
        $post["pos"] = $row["pos"];
        $post["company"] = $row["company"];
        $post["phone"]  = $row["phone"];
        $post["ext"]  = $row["ext"]; 
        $post["fax"]  = $row["fax"]; 
        $post["email"]  = $row["email"];       
        $post["message"]  = $row["message"];

       
        array_push($response["posts"], $post);
    }
    
   
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}

?>	