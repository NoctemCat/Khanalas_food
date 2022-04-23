<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
if(isset($_GET['type'])){
    $type = $_GET['type'];
    $result = mysqli_query($db->con, "SELECT *FROM products WHERE `type` = '$type' AND count > 0") or die(mysqli_error());
}else{
    $result = mysqli_query($db->con, "SELECT *FROM products WHERE count > 0") or die(mysqli_error());
}
// get all products from products table
 
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["products"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["pid"] = $row["pid"];
        $product["name"] = $row["name"];
        $product["type"] = $row["type"];
        $product["count"] = $row["count"];
        $product["price"] = $row["price"];
        $product["created_at"] = $row["created_at"];
        $product["updated_at"] = $row["updated_at"];
 
        // push single product into final response array
        array_push($response["products"], $product);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>