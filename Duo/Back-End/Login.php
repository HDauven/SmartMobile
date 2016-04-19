<?php
	require_once 'DB_Functions.php';
	$db = new DB_Functions();

	// JSON response array 
	$response = array("error" => FALSE);
	
	if (isset($_POST['email']) && isset($_POST['password'])) {
		// receiving the POST parameters
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		// get the user via the emailaddress and password
		$user = $db->getUserByEmailAndPassword($email, $password);
		
		if ($user != false) {
			// user exists
			$response["error"] = FALSE;
			$response["uid"] = $user["unique_id"];
			$response["user"]["name"] = $user["name"];
			$response["user"]["email"] = $user["email"];
			$response["user"]["created_at"] = $user["created_at"];
			$response["user"]["updated_at"] = $user["updated_at"];
			echo json_encode($response);
		} else {
			// failed to find user with the appropriate credentials
			$response["error"] = TRUE;
			$response["error_msg"] = "Login credentials are wrong";
			echo json_encode($response);
		}
	} else {
		// A POST parameter is missing
		$response["error"] = TRUE;
		$response["error_msg"] = "Required parameters (email or password) are missing!";
		echo json_encode($response);
	}
?>