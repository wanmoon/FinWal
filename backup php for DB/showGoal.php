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

//ending_date, description_goal, status_goal, budget_goal, savingplan, suggest_cost, current_goal
$sql = "SELECT * FROM goal WHERE cust_id = '$cust_id'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['goal_id'] . "," . $row['cust_id'] . "," . $row['ending_date'] . "," . $row['description_goal'] . "," . $row['status_goal'] . "," . $row['budget_goal'] . "," . $row['savingplan'] . "," . $row['suggest_cost'] . "," . $row['current_goal'] . "\n";
    }
} else {
    echo "0 ";
}

$conn->close();
?>