<?php //query for select 'all' transaction of one user
//echo "start\n";
$servername = "localhost";
$username = "root";
$password = "DmA3@AF3";
$dbname = "Finwal";

$conn = mysqli_connect($servername, $username, $password, $dbname);
mysqli_set_charset($conn,"utf8");



function line(){
global $conn;
// Check connection
if (!$conn) {
  die('Could not connect: ' . mysqli_connect_error());
} else {
//	echo "connect\n";
}

$cust_id = $_GET["cust_id"];

$sql = " SELECT MONTH(timestamp) as month  , SUM(cost) as income FROM transaction
WHERE transaction = 'Income' AND category = 'Extra income'
AND YEAR(CURDATE())=YEAR(timestamp)
AND cust_id = '$cust_id' GROUP BY month ";

$result = $conn->query($sql);


if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['category'] . "," . $row['SUM(cost)'] . "," ;
    }
}else {
    echo "ERROR : " . $conn->error;
}
}
line();

$conn->close();
?>
