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
$status;// = " AND status_goal != 'Deleted' ";
$sort;

if ($flagSort == 0) { //unacheive =0
    $status = " AND status_goal = 'On Process' ";
	$sort = "status_goal DESC";
} elseif ($flagSort == 1) { //achieve =1
    $status = " AND status_goal = 'Achieved' ";
	$sort = "status_goal ASC";
} elseif ($flagSort == 2) { //price low-high =2
    $status = " AND status_goal != 'Deleted' ";
	$sort = "budget_goal ASC";
} elseif ($flagSort == 3) { //price high-low =3
    $status = " AND status_goal != 'Deleted' ";
	$sort = "budget_goal DESC";
} elseif ($flagSort == 5) { //deleted
    $sort = "ending_date DESC";
    $status = " AND status_goal = 'Deleted' ";
} elseif ($flagSort == 6) { //deleted
    $sort = "ending_date DESC";
    $status = " AND status_goal = 'Unachieve' ";
} else {
	$sort = "ending_date DESC"; //time = 4
}

$sql = "SELECT goal_id, ending_date, description_goal, status_goal, budget_goal, savingplan, suggest_cost, current_goal FROM goal WHERE cust_id = '$cust_id' " . $status . "  ORDER BY $sort";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['goal_id'] . "," . $row['ending_date'] . "," . $row['description_goal'] . "," . $row['status_goal'] . "," . $row['budget_goal'] . "," . $row['savingplan'] . "," . $row['suggest_cost'] . "," . $row['current_goal'] . "\n";
    }
} else {
    echo "ERROR : " . $conn->error;
}

$conn->close();
?>
