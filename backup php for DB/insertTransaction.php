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
$description = $_GET["description"];
$cost = $_GET["cost"];
$transaction = $_GET["transaction"];
$category = $_GET["category"];

//insert transaction to DB
$sql = "INSERT INTO transaction(cust_id,timestamp,description,cost,transaction,category) VALUES ('$cust_id',current_timestamp,'$description','$cost','$transaction','$category');";
//$sql = "INSERT INTO transaction(cust_id,timestamp,description,cost,transaction,category) VALUES('fjgnlg',current_timestamp,'2','2','2','2')";
echo $sql;

//$result = $conn->query($sql);
//$json = array();

// if ($result->num_rows > 0) {
//     // output data of each row
//     while($row = $result->fetch_assoc()) {
//         echo $row['pName'] . "," . $row['date'] . "," . $row['timeInterval'] . "," . $row['gLicense'] . "\n";

//         //echo ;
//         //$json['parkId'][]=$row;
//         //echo "$json";
//     }
// } else {
//     echo "0 ";
// }

if ($conn->query($sql) === TRUE) {
  echo "Add transaction successful";
} else {
  echo "ERROR : " . $conn->error;
}


$conn->close();
//echo json_encode($json);
?>
