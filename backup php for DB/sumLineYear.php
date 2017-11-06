<?php //query for select 'all' transaction of one user
//echo "start\n";
$servername = "localhost";
$username = "root";
$password = "DmA3@AF3";
$dbname = "Finwal";

$conn = mysqli_connect($servername, $username, $password, $dbname);
mysqli_set_charset($conn,"utf8");

global $conn;
// Check connection
if (!$conn) {
  die('Could not connect: ' . mysqli_connect_error());
} else {
//	echo "connect\n";
}

$cust_id = $_GET["cust_id"];
//income
$sql_income = "SELECT MONTH(timestamp) as month, SUM(cost) as income FROM transaction
        WHERE transaction='Income' AND YEAR(CURDATE()) =YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_income = $conn->query($sql_income);

if ($result_income->num_rows > 0) {
    // output data of each row
    while($row = $result_income->fetch_assoc()) {
        echo "Income" . "," .  $row['month'] . "," . $row['income'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//expense
$sql_expense = "SELECT MONTH(timestamp) as month, SUM(cost) as expense FROM transaction
        WHERE transaction='Expense' AND YEAR(CURDATE()) =YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_expense = $conn->query($sql_expense);

if ($result_expense->num_rows > 0) {
    // output data of each row
    while($row = $result_expense->fetch_assoc()) {
        echo "Expense" . "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}



//Income
//gift
$sql_gift = "SELECT month(timestamp) as month , SUM(cost) as income
            FROM transaction
            WHERE transaction = 'Income' AND  category = 'Gift' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_gift = $conn->query($sql_gift);

if ($result_gift->num_rows > 0) {
    // output data of each row
    while($row = $result_gift->fetch_assoc()) {
        echo "Gift" .  "," . $row['month'] . "," . $row['income'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//salary
$sql_salary = "SELECT month(timestamp) as month , SUM(cost) as income
            FROM transaction
            WHERE transaction = 'Income' AND  category = 'Salary' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_salary = $conn->query($sql_salary);

if ($result_salary->num_rows > 0) {
    // output data of each row
    while($row = $result_salary->fetch_assoc()) {
        echo "Salary" .  "," . $row['month'] . "," . $row['income'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//Extra income
$sql_extra = "SELECT month(timestamp) as month , SUM(cost) as income
            FROM transaction
            WHERE transaction = 'Income' AND  category = 'Extra income' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_extra = $conn->query($sql_extra);

if ($result_extra->num_rows > 0) {
    // output data of each row
    while($row = $result_extra->fetch_assoc()) {
        echo "Extra income" .  "," . $row['month'] . "," . $row['income'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//Loan
$sql_loan = "SELECT month(timestamp) as month , SUM(cost) as income
            FROM transaction
            WHERE transaction = 'Income' AND category = 'Loan' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_loan = $conn->query($sql_loan);

if ($result_loan->num_rows > 0) {
    // output data of each row
    while($row = $result_loan->fetch_assoc()) {
        echo "Loan" .  "," . $row['month'] . "," . $row['income'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//Family and Personal
$sql_family = "SELECT month(timestamp) as month , SUM(cost) as income
            FROM transaction
            WHERE transaction = 'Income' AND category = 'Loan' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_family = $conn->query($sql_family);

if ($result_family->num_rows > 0) {
    // output data of each row
    while($row = $result_family->fetch_assoc()) {
        echo "Family and Personal" .  "," . $row['month'] . "," . $row['income'] . "-";
    }
} else {
    echo "" . $conn->error;
}


///////////////////////////Expense///////////////////////////////
//bill
$sql_bill = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Bill' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_bill = $conn->query($sql_bill);

if ($result_bill->num_rows > 0) {
    // output data of each row
    while($row = $result_bill->fetch_assoc()) {
        echo "Bill" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//education
$sql_education= "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Education' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_education = $conn->query($sql_education);

if ($result_education->num_rows > 0) {
    // output data of each row
    while($row = $result_education->fetch_assoc()) {
        echo "Education" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//Entertainment
$sql_entertainment = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Entertainment' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_entertainment = $conn->query($sql_entertainment);

if ($result_entertainment ->num_rows > 0) {
    // output data of each row
    while($row = $result_entertainment->fetch_assoc()) {
        echo "Entertainment" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//food and drink
$sql_food = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Food and Drink' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_food = $conn->query($sql_food);

if ($result_food->num_rows > 0) {
    // output data of each row
    while($row = $result_food->fetch_assoc()) {
        echo "Food and Drink" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//shopping
$sql_shopping = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Shopping' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_shopping = $conn->query($sql_shopping);

if ($result_shopping->num_rows > 0) {
    // output data of each row
    while($row = $result_shopping->fetch_assoc()) {
        echo "Shopping" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}
//transport
$sql_transport= "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Transport' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_transport = $conn->query($sql_transport);

if ($result_transport->num_rows > 0) {
    // output data of each row
    while($row = $result_transport->fetch_assoc()) {
        echo "Transport" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//travel
$sql_travel = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Travel' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_travel = $conn->query($sql_travel);

if ($result_travel->num_rows > 0) {
    // output data of each row
    while($row = $result_travel->fetch_assoc()) {
        echo "Travel" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//Family and personal
$sql_familyex = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Family and Personal' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_familyex  = $conn->query($sql_familyex);

if ($result_familyex ->num_rows > 0) {
    // output data of each row
    while($row = $result_familyex ->fetch_assoc()) {
        echo "Family and Personal Expense" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//healthcare
$sql_healthcare = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Healthcare' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_healthcare = $conn->query($sql_healthcare);

if ($result_healthcare->num_rows > 0) {
    // output data of each row
    while($row = $result_healthcare->fetch_assoc()) {
        echo "Healthcare" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}

//saving
$sql_saving = "SELECT month(timestamp) as month , SUM(cost) as expense
            FROM transaction
            WHERE transaction = 'Expense' AND category = 'Saving and Investment' AND  YEAR(CURDATE())=YEAR(timestamp) AND cust_id = '$cust_id' GROUP BY month";

$result_saving = $conn->query($sql_saving);

if ($result_saving->num_rows > 0) {
    // output data of each row
    while($row = $result_saving->fetch_assoc()) {
        echo "Saving and Investment" .  "," . $row['month'] . "," . $row['expense'] . "-";
    }
} else {
    echo "" . $conn->error;
}






$conn->close();
?>
