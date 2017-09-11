<?php
//echo "start\n";
$servername = "localhost";
$username = "root";
$password = "DmA3@AF3";
$dbname = "Finwal";

  // Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);

mysqli_set_charset($conn,"utf8");

  // Check connection
if (!$conn) {
  die('Could not connect: ' . mysqli_connect_error());
} else {
//	echo "connect\n";
}

$cust_id = $_GET["cust_id"];
$ending_date = $_GET["ending_date"];
$description_goal = $_GET["description_goal"];
$status_goal = $_GET["status_goal"];
$cost_goal = $_GET["cost_goal"];
$savingplan = $_GET["savingplan"];

$sql = "INSERT INTO goal(cust_id, ending_date, description_goal, status_goal, cost_goal, savingplan) VALUES ('$cust_id','$ending_date','$description_goal','$status_goal','$cost_goal', '$savingplan');";
//$sql = "INSERT INTO transaction(cust_id,timestamp,description,cost,transaction,category) VALUES('fjgnlg',current_timestamp,'2','2','2','2')";
echo $sql;

if ($conn->query($sql) === TRUE) {
  echo "Add bill successful";
} else {
  echo "ERROR : " . $conn->error;
}

$conn->close();
?>
