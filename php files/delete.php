<?php


require("config.php");

//initial query
$query = "DELETE FROM cards WHERE card_id = :card_id";
$query_params = array(':card_id' => $_POST["card_id"]);
//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

    $response["success"] = 1;
    $response["message"] = "card successfuly deleted";

    die(json_encode($response));
?>	