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

if ($flagSort == 0) { //unacheive =0
	$sort = "status_goal DESC";
} elseif ($flagSort == 1) { //achieve =1
	$sort = "status_goal ASC";
} elseif ($flagSort == 2) { //price low-high =2
	$sort = "budget_goal ASC";
} elseif ($flagSort == 3) { //price high-low =3
	$sort = "budget_goal DESC";
} else {
	$sort = "ending_date DESC"; //time = 4
}

$sql = "SELECT ending_date, description_goal, status_goal, budget_goal FROM goal WHERE cust_id = '$cust_id' ORDER BY $sort";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['ending_date'] . "," . $row['description_goal'] . "," . $row['status_goal'] . "," . $row['budget_goal'] . "\n";
    }
} else {
    echo "ERROR : " . $conn->error;
}

$conn->close();
?>
