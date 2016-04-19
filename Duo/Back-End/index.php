<?php
error_reporting(E_ALL ^ E_DEPRECATED);
/**
 * File to handle all API requests
 * Accepts GET and POST requests
 * 
 * Each request will be identified by a TAG
 * Response will be in a JSON data format

  /**
 * check for POST request 
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include DB_Functions
    require_once 'DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    // checking tag
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByEmailAndPassword($email, $password);
        if ($user != false) {
            // user is found
            $response["error"] = FALSE;
            $response["user_id"] = $user["user_id"];
            $response["user"]["name"] = $user["user_name"];
            $response["user"]["email"] = $user["user_email"];
            echo json_encode($response);
        } else {
            // user is not found
            // echo json with error = 1
            $response["error"] = TRUE;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } else if ($tag == 'register') {
        // Request type is Register new user
        $name = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check if the user already exists
        if ($db->doesUserExist($email)) {
            // the user already exists - error response
            $response["error"] = TRUE;
            $response["error_msg"] = "User already exists";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($name, $email, $password);
            if ($user) {
                // user stored successfully
                $response["error"] = FALSE;
                $response["uid"] = $user["user_id"];
                $response["user"]["name"] = $user["user_name"];
                $response["user"]["email"] = $user["user_email"];
                echo json_encode($response);
            } else {
                // failed to store the user
                $response["error"] = TRUE;
                $response["error_msg"] = "Error occurred in User Registration";
                echo json_encode($response);
            }
        }
    } else {
        // failed to store the user
        $response["error"] = TRUE;
        $response["error_msg"] = "Unknown 'tag' value. It should be either 'login' or 'register'";
        echo json_encode($response);
    }
} else {
    ?><html><head>
	<title>Grapp | Database API</title>
</head>
<body style="background:#2c3e50;">
	<div style="margin:0 auto; margin-top:200px;width:60%;">
		<img src="api_logo.png" alt="Grapp">
	</div>

</body></html><?php
}
?>