<?php


require("config.php");

$query = "Select * FROM cards WHERE card_id = :card_id";
$query_params = array(':card_id' => $_POST["card_id"]);


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
    $response["message"] = "card successfuly loaded";

$row = $stmt->fetch(PDO::FETCH_ASSOC);


$response["name"] = $row["name"];
$response["company"] = $row["company"];
$response["pos"] = $row["pos"];
$response["address"] = $row["address"];
$response["phone"]  =$row["phone"];
$response["email"]  = $row["email"];
$response["ext"]  = $row["ext"];
$response["fax"]  = $row["fax"];
$response["twitter"]  = $row["twitter"];

die(json_encode($response));
?>		