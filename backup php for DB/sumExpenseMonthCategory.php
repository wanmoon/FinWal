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

$sql = " SELECT category, SUM(cost) FROM transaction WHERE transaction = 'Expense'
AND MONTH(CURDATE())=MONTH(timestamp) AND  YEAR(CURDATE())=YEAR(timestamp)
AND cust_id = '$cust_id' GROUP BY category ";

$result = $conn->query($sql);


if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['category'] . "," . $row['SUM(cost)'] . "," ;
    }
}else {
    echo "0 ";
}


$conn->close();
?>
