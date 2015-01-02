<!DOCTYPE html PUBLIC "-//w3c//dtd xhtml 1.0
	Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/zhtml">
<head>
<meta http-equiv="Content-type"
content="text/html;charset=utf-8">

<title>buseniss card</title>

<style type="text/css">


h1				{font-family:Courier New;
                                font-size:90px;
				text-align: center;
				margin: 0px;}

h3				{font-family:book antique;
                                font-size:80px;
				text-align: center;
				margin:0px}
				
h4				{font-family:book antique;
                                font-size:70px;
				text-align: center;
				margin-bottom:0px;
				margin-top:5px}



body 			{color: #44444;
}

div#container	{border: solid 2px #0f0f0f;
				width: 80%;
				padding: 20px;
				background-color:#E1E3F2;
				margin-left: auto;
				margin-right: auto;
				box-shadow: 10px 10px 5px #888;}
				
				
div#info		{width: 300px;
				padding: 20px;
				margin-left: auto;
				margin-right: auto;}

				
div#info td		{font-size:40px;
				text-align:left;}


table			{border-collapse: collapse;}		
</style>


</head>
<body>
<div id="container">

<?php


require("config.php");

//initial query
$query = "Select * FROM cards WHERE card_id = :card_id";
$query_params = array(':card_id' => $_GET["card_id"]);
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



$row = $stmt->fetch(PDO::FETCH_ASSOC);


        echo '<h1>'.$row["name"].'<h1>';
        echo '<h3>'.$row["pos"].'<h3>';
        echo '<h4>'.$row["company"].'<h4>';

?>

<div id="info" >

<table>
<tr>

<td>Address</td>
<td>:</td>
<?php
    


		echo '<td><a href="https://maps.google.com/?q='. $row["address"] . '&t=m">'.$row["address"].'</a></td>';

echo "</tr>
<tr>
<td>phone no.</td>
<td>:</td>";

echo '<td><a href="tel:'.$row["phone"].'">'.$row["phone"].'</a></td>';

echo "</tr>
<tr>
<td>Ext.</td>
<td>:</td>
";
echo '<td>'.$row["ext"].'</td>';


echo "</tr>
<tr>
<td>Fax</td>
<td>:</td>
";

echo '<td>'.$row["fax"].'</td>';



echo "</tr>
<tr>
<td>Email</td>
<td>:</td>";

echo '<td><a href="mailto:'.$row["email"].'">'.$row["email"].'</a> </td>';

echo "</tr>
<tr>
<td>twitter</td>
<td>:</td>";

echo '<td><a href="https://twitter.com/'.$row["twitter"].'"/>'.$row["twitter"].'</a></td>';

?>
</tr>
</table>


</div>

</div>

</body>
</html>		