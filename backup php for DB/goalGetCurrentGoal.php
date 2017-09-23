

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
$goal_id = $_GET["goal_id"];

//SELECT current_goal FROM `goal` WHERE cust_id = 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1' AND goal_id = '38'
//UPDATE goal SET current_goal = '20' WHERE goal_id = '38' AND cust_id = 'NvxF5IzFIlVDRPvGFCq2QJAJ0Kk1'

//ending_date, description_goal, status_goal, budget_goal, savingplan, suggest_cost, current_goal
$sql = "SELECT current_goal FROM goal WHERE cust_id = '$cust_id' AND goal_id = '$goal_id'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['current_goal'] . "\n";
    }
} else {
    echo "ERROR : " . $conn->error;
}

$conn->close();
?>
