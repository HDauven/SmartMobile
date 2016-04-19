<?php
	require_once 'DB_Functions.php';
	$db = new DB_Functions();

	// JSON response array 
	$response = array("error" => FALSE);
	
	if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
		// receiving the post parameters
		$name = $_POST['name'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		// check if the user already exists, with the same emailaddress
		if ($db->doesUserExist($email)) {
			// user already exists
			$response["error"] = TRUE;
			$response["error_msg"] = "User already exists!";
			echo json_encode($response);
		} else {
			// create a new user 
			$user = $db->storeUser($name, $email, $password);
			
			if ($user) {
				// user is stored successfully
				$response["error"] = FALSE;
				$response["uid"] = $user["unique_id"];
				$response["user"]["name"] = $user["name"];
				$response["user"]["email"] = $user["email"];
				$response["user"]["created_at"] = $user["created_at"];
				$response["user"]["updated_at"] = $user["updated_at"];
				echo json_encode($response);
			} else {
				// failed to store user
				$response["error"] = TRUE;
				$response["error_msg"] = "Unknown error occurred while registering";
				echo json_encode($response);
			}
		}
	} else {
		$response["error"] = TRUE;
		$response["error_msg"] = "Required parameters (name, email or password) are missing!";
		echo json_encode($response);
	}
?>