<?php //query for select 'all' transaction of one user
//echo "start\n";
$servername = "localhost";
$username = "root";
$password = "DmA3@AF3";
$dbname = "Finwal";

$conn = mysqli_connect($servername, $username, $password, $dbname);
mysqli_set_charset($conn,"utf8");

// Check connection
if (!$conn) {
  die('Could not connect: ' . mysqli_connect_error());
} else {
//	echo "connect\n";
}

$cust_id = $_GET["cust_id"];

$sql1 = "SELECT SUM(cost) AS 'Expense' FROM transaction WHERE transaction = 'Expense'
AND MONTH(CURDATE())=MONTH(timestamp) AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id'";

$sql2 = "SELECT SUM(cost) AS 'Income' FROM transaction WHERE transaction = 'Income'
AND MONTH(CURDATE())=MONTH(timestamp) AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id'";

$expense = $conn->query($sql1);
$income = $conn->query($sql2);

if ($expense->num_rows > 0 && $income->num_rows > 0) {
    // output data of each row
    echo $row['Expense'] . "," . $row['Income'];
} else {
    echo "ERROR : " . $conn->error;
}

$conn->close();
?>
