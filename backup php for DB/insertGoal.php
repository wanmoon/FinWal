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
$budget_goal = $_GET["budget_goal"];
$savingplan = $_GET["savingplan"];
$suggest_cost = $_GET["suggest_cost"];

$sql = "INSERT INTO goal(cust_id, ending_date, description_goal, budget_goal, savingplan, suggest_cost) VALUES ('$cust_id','$ending_date','$description_goal','$budget_goal','$savingplan', '$suggest_cost');";

echo $sql;

if ($conn->query($sql) === TRUE) {
  echo "Add bill successful";
} else {
  echo "ERROR : " . $conn->error;
}

$conn->close();
?>
