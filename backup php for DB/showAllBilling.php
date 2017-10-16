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
$status = " AND status_bill != 'Deleted' ";
$sort;

if ($flagSort == 0) {
	$sort = "status_bill ASC";
} elseif ($flagSort == 1) {
	$sort = "status_bill DESC";
} elseif ($flagSort == 3) {
	$status = " AND status_bill = 'Deleted' ";
    $sort = "deadline ASC";
} else {
	$sort = "deadline ASC";
}

$sql = "SELECT period, description_bill, status_bill, deadline FROM period WHERE cust_id = '$cust_id'" . $status . " ORDER BY $sort";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['period'] . "," . $row['description_bill'] . "," . $row['status_bill'] . "," . $row['deadline'] . "\n";
    }
} else {
    echo "ERROR : " . $conn->error;
}

$conn->close();
?>