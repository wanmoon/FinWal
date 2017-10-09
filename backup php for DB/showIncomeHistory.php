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

//income
if ($flagSort == 1) { //Gift =2
    $sort = " AND category = 'Gift' ";
} elseif ($flagSort == 2) { //Saraly=3
    $sort = " AND category = 'Salary' ";
} elseif ($flagSort == 3) { //loan =4
    $sort = " AND category = 'Loan' ";
} elseif ($flagSort == 4) { //family income =5
    $sort = " AND category = 'Family and Personal' ";
} elseif ($flagSort == 5) { //extra income=6
    $sort = " AND category = 'Extra income' ";
}  else {
	$sort = ""; //default : all = 0
}

$sql = "SELECT timestamp, description, cost, transaction, category FROM transaction WHERE cust_id='$cust_id' AND transaction='Income'" . $sort . "ORDER BY timestamp DESC";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['timestamp'] . "," . $row['description'] . "," . $row['cost'] . "," . $row['transaction'] . "," . $row['category'] . "\n";
    }
} else {
    echo "ERROR : " . $conn->error;
}

$conn->close();
?>