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

//SELECT MONTH(timestamp) as month, SUM(cost) as income FROM transaction WHERE transaction='Income' AND cust_id='1NzulQpFxYadvpJsgXn3dX2dxLw2' GROUP BY month

$sql = "SELECT MONTH(timestamp) as month, SUM(cost) as expense FROM transaction WHERE transaction='Expense' AND YEAR(CURDATE()) =YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['month'] . "," . $row['expense'] . ",";
    }
} else {
    echo "ERROR";
}

$conn->close();
?>
