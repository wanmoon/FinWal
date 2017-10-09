<?php
//echo "start\n";
$servername = "localhost";
$username = "root";
$password = "DmA3@AF3";
$dbname = "Finwal";

  // Create connection
// $conn = mysqli_connect($servername, $username, $password, $dbname);
$conn = mysqli_connect($servername, $username, $password, $dbname);

mysqli_set_charset($conn,"utf8");

  // Check connection
if (!$conn) {
  die('Could not connect: ' . mysqli_connect_error());
} else {
//	echo "connect\n";
}

$cust_id = $_GET["cust_id"];
$email = $_GET["email"];

//insert transaction to DB
$sql = "INSERT INTO customer(cust_id,email) VALUES ('$cust_id','$email');";
//$sql = "INSERT INTO transaction(cust_id,timestamp,description,cost,transaction,category) VALUES('fjgnlg',current_timestamp,'2','2','2','2')";
echo $sql;

if ($conn->query($sql) === TRUE) {
  echo "Add transaction successful";
} else {
  echo "ERROR : " . $conn->error;
}

$conn->close();
//echo json_encode($json);
?>
