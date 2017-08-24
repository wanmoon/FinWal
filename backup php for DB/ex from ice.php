<?php
$servername = "localhost";
$username = "root";
$password = "GQ#h43Fe";
$dbname = "parkhere";

  // Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);

  // Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}

$uId = $_GET["uId"];

//ดึงค่า ประวัติ เฉพาะที่จอดไปแล้ว pStatus = 0 = เป็นอดีต
$sql = "SELECT pName,date,timeInterval,gLicense
FROM `reserve` as T1 left join `guest` as T2 on T1.gId = T2.gId
LEFT join parkArea as T3 on T1.pId = T3.pId
WHERE status = '1' ORDER BY date ASC";
        //ON guest.gId = reserve.gId

$result = $conn->query($sql);
//$json = array();

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        echo $row['pName'] . "," . $row['date'] . "," . $row['timeInterval'] . "," . $row['gLicense'] . "\n";

        //echo ;
        //$json['parkId'][]=$row;
        //echo "$json";
    }
} else {
    echo "0 ";
}

$conn->close();
//echo json_encode($json);
?>
