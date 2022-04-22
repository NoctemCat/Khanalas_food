<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['pid']) && (isset($_POST['name']) || isset($_POST['count']) || isset($_POST['price']) || isset($_POST['description']))) {
    $pid = $_POST['pid'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';
    // connecting to db
    $db = new DB_CONNECT();
    // mysql update row with matched pid
    $result = mysqli_query($db->con, "SELECT *FROM products WHERE pid = $pid");
    if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {
            $result = mysqli_fetch_array($result);

            $name = $result["name"];
            $count = $result["count"];
            $price = $result["price"];
            $description = $result["description"];

            if(isset($_POST['name']){
                $name = $_POST['name'];
            }
            if(isset($_POST['count']){
                $name = $_POST['count'];
            }
            if(isset($_POST['price']){
                $name = $_POST['price'];
            }
            if(isset($_POST['description']){
                $name = $_POST['description'];
            }


            // mysql update row with matched pid
            $result = mysqli_query($db->con, "UPDATE products SET name = '$name', count = '$count', price = '$price', description = '$description' WHERE pid = $pid");

            // check if row inserted or not
            if ($result) {
                // successfully updated
                $response["success"] = 1;
                $response["message"] = "Product successfully updated.";

                // echoing JSON response
                echo json_encode($response);
            } else {
                    // successfully updated
                $response["success"] = 0;
                $response["message"] = "Product failed to update.";

                // echoing JSON response
                echo json_encode($response);
            }
        }
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>