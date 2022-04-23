<?php 
require_once __DIR__ . '/db_connect.php';
// connecting to db
$db = new DB_CONNECT();
$conn = $db->con;

//an array to display response
$response = array();

//if it is an api call 
//that means a get parameter named api call is set in the URL 
//and with this parameter we are concluding that it is an api call 
if(isset($_GET['apicall'])){
    switch($_GET['apicall']){
        case 'add':
        //checking the parameters required are available or not 
            if(isTheseParametersAvailable(array('user_id','prod_id', 'prod_name','count'))){
                //getting the values 
                $prod_id = $_POST['prod_id']; 
                $user_id = $_POST['user_id']; 
                $prod_name = $_POST['prod_name']; 
                $count = $_POST['count']; 
                $address= null;
                $time = null;

                if(isset($_POST['address'])){
                    $address = $_POST['address']; 
                }
                if(isset($_POST['time'])){
                    $time = $_POST['time']; 
                }

                $stmt = $conn->prepare("SELECT * FROM cart WHERE `prod_id` = '$prod_id' AND `user_id` = '$user_id'");
                $result = $stmt->execute();
                $stmt->store_result();

                //if the user already exist in the database 
                if($stmt->num_rows > 0){
                    $stmt->close();

                    $stmt = $conn->prepare("UPDATE `cart` SET `count`=`count` + 1 WHERE `user_id` = ? AND `prod_id` = ?");
                    $stmt->bind_param("ss", $user_id, $prod_id);
                    if($stmt->execute()){
                        $stmt->close();
                        $response['error'] = false;
                        $response['message'] = 'Incremented by one';
                    }else{
                        $response['error'] = true;
                        $response['message'] = 'Failed to increment';
                    }
                }else{
                    $stmt->close();

                    //if user is new creating an insert query 
                    $stmt = $conn->prepare("INSERT INTO cart (prod_id, user_id, prod_name, count, address, time) VALUES (?, ?, ?, ?, ?, ?)");
                    $stmt->bind_param("ssssss", $prod_id, $user_id, $prod_name, $count, $address, $time);

                    //if the user is successfully added to the database 
                    if($stmt->execute()){
                        $stmt->close();

                        //adding the user data in response 
                        $response['error'] = false; 
                        $response['message'] = 'Successfully added to cart'; 
                    }
                }

            }else{
                $response['error'] = true; 
                $response['message'] = 'required parameters are not available'; 
            }

        break; 

        case 'delete':
            //for login we need the username and password 
            if(isTheseParametersAvailable(array('cart_id'))){
                $cart_id = $_POST['cart_id'];

                $stmt = $conn->prepare("DELETE FROM `cart` WHERE `id` = ?");
                $stmt->bind_param("s", $cart_id);

                if($stmt->execute()){
                    $stmt->close();
                    
                    $response['error'] = false; 
                    $response['message'] = 'Successfuly deleted'; 
                }else{
                    $response['error'] = true; 
                    $response['message'] = 'This item doesnt exist in cart';
                }
            }
        break;

        case 'getuser':
            if(isTheseParametersAvailable(array('user_id'))){
                $user_id = $_POST['user_id'];

                //creating the query 
                $stmt = $conn->prepare("SELECT * FROM cart WHERE `user_id` = '$user_id'");
                $stmt->execute();
                $stmt->store_result();

                $response["cart_products"] = array();

                $cart_id = null;
                $prod_id = null;
                $user_id = null;
                $prod_name = null;
                $count = null;
                $address = null;
                $time = null;
                $stmt->bind_result($cart_id, $prod_id, $user_id, $prod_name, $count, $address,$time);
                while($stmt->fetch()){
                    $product = array(
                        'cart_id'=>$cart_id, 
                        'prod_id'=>$prod_id, 
                        'user_id'=>$user_id,
                        'prod_name'=>$prod_name,
                        'count'=>$count,
                        'address'=>$address,
                        'time'=>$time
                    );

                    array_push($response["cart_products"], $product);
                }

                $response['error'] = false; 
                $response['message'] = 'Got all info';
            }
        break;

        case 'buy':
            if(isTheseParametersAvailable(array('user_id'))){
                $user_id = $_POST['user_id'];

                $stmt = $conn->prepare("DELETE FROM `cart` WHERE `user_id` = ?");
                $stmt->bind_param("s", $user_id);

                if($stmt->execute()){
                    $stmt->close();
                    
                    $response['error'] = false; 
                    $response['message'] = 'Successfuly bought'; 
                }else{
                    $response['error'] = true; 
                    $response['message'] = 'User didnt bought anything';
                }
            }
        break;

        default: 
            $response['error'] = true; 
            $response['message'] = 'Invalid Operation Called';
    }

}else{
    //if it is not api call 
    //pushing appropriate values to response array 
    $response['error'] = true; 
    $response['message'] = 'Invalid API Call';
}

//displaying the response in json structure 
echo json_encode($response);

//function validating all the paramters are available
//we will pass the required parameters to this function 
function isTheseParametersAvailable($params){
    //traversing through all the parameters 
    foreach($params as $param){
        //if the paramter is not available
        if(!isset($_POST[$param])){
        //return false 
        return false; 
        }
    }
    //return true if every param is available 
    return true; 
}
