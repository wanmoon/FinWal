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
$flagSort = $_GET["flagSort"];
$sort;

if ($flagSort == 0) { //cate a-z =0
	$sort = "category ASC";
} elseif ($flagSort == 1) { //price low-high =1
	$sort = "cost ASC";
} elseif ($flagSort == 2) { //price high-low =2
	$sort = "cost DESC";
} else {
	$sort = "timestamp DESC"; //time = 3
}

$sql = "SELECT timestamp, description, cost, transaction, category FROM transaction WHERE cust_id='$cust_id' ORDER BY $sort";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['timestamp'] . "," . $row['description'] . "," . $row['cost'] . "," . $row['transaction'] . "," . $row['category'] . "\n";
    }
} else {
    echo "0 ";
}

$conn->close();
?>