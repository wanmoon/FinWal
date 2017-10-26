<?php //query for select 'all' transaction of one user
//echo "start\n";
$servername = "localhost";
$username = "root";
$password = "DmA3@AF3";
$dbname = "Finwal";

$conn = mysqli_connect($servername, $username, $password, $dbname);
mysqli_set_charset($conn,"utf8");



function lineIncomeYear(){
  global $conn;
  // Check connection
  if (!$conn) {
    die('Could not connect: ' . mysqli_connect_error());
  } else {
  //	echo "connect\n";
  }

  $cust_id = $_GET["cust_id"];
  // $arr_gift = array();
  // $sql_gift = "SELECT month(timestamp) as month ,SUM(cost) AS cost
  //             FROM transaction
  //             WHERE cust_id = '$cust_id' AND YEAR(CURDATE())=YEAR(timestamp) AND category = 'Gift' GROUP BY month";
  // $q_gift = $conn->query($sql_gift);
  // while($f_gift = $q_gift->fetch_array()){
  //   $arr_gift[$f_gift['month']] = $f_gift['cost'];
  // }
  //
  // $num=1;
  // while($num!=13){
  //   if($arr_gift[$num]==''){
  //     $arr_gift[$num] = 0;
  //   }
  //   $num++;
  // }


  $sql_gift = SELECT month(timestamp) as month ,SUM(cost) AS cost
              FROM transaction
              WHERE cust_id = '$cust_id' AND YEAR(CURDATE())=YEAR(timestamp) AND category = 'Gift' GROUP BY month";

  $result_gift = $conn->query($sql_gift);

  if ($result_gift->num_rows > 0) {
      // output data of each row
      while($row = $result_gift->fetch_assoc()) {
          echo $row['month'] . "," . $row['income'] . ",";
      }
  } else {
      echo "ERROR : " . $conn->error;
  }



}
lineIncomeYear();

$conn->close();
?>
