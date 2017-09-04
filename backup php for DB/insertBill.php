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
$period = $_GET["period"];
$description_bill = $_GET["description_bill"];
$status_bill = $_GET["status_bill"];
$deadline = $_GET["deadline"];
//$category = $_GET["category"];

//insert transaction to DB
//???? transaction ID OR custID
$sql = "INSERT INTO period(cust_id, period, description_bill, status_bill, deadline) VALUES ('$cust_id','$period','$description_bill','$status_bill','$deadline');";
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
  echo "Add bill successful";
} else {
  echo "ERROR : " . $conn->error;
}


$conn->close();
//echo json_encode($json);
?>
