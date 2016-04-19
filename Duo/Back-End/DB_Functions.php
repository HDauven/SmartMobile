/*==============================================================*/
/*	DB_Functions.php						                    */
/*	Grapp					  							        */
/*	by Hein Dauven							                    */
/*==============================================================*/
// Database handler class; contains code and queries to insert, 
// update and retrieve data from the database.
<?php
class DB_Functions {

	private $conn;

    function __construct() { // constructor
        require_once 'DB_Connect.php';
        // connect to database
        $db = new DB_Connect();
		$this->conn = $db->connect();
    }

    function __destruct() { // destructor
        
    }

    /**
     * Store user details
     */
    public function storeUser($name, $email, $password) {
		$uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
		
		$stmt = $this->conn->prepare("INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at) VALUES(?, ?, ?, ?, ?, NOW())");
		$stmt->bind_param("sssss", $uuid, $name, $email, $encrypted_password, $salt);
		$result = $stmt->execute();
		$stmt->close();
		
        // check for result
        if ($result) {
            // getting the details
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
			$stmt->bind_param("s", $email);
			$stmt->execute();
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			
            // return details
            return $user;
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
		$stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
		$stmt->bind_param("s", $email);
		
        // check for result 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			
			// verifying the users password
            $salt = $user['salt'];
            $encrypted_password = $user['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check if password is equal
            if ($encrypted_password == $hash) {
				// user authentication is succesful
                return $user;
            }
        } else {
            return NULL;
        }
    }

    /**
     * Check if the user exists or not
     */
    public function doesUserExist($email) {
		$stmt = $this->conn->prepare("SELECt email FROM users WHERE email = ?");
		$stmt->bind_param("s", $email);
		$stmt->execute();
		$stmt->store_result();
		
        if ($stmt->num_rows > 0) {
            // user exists
			$stmt->close();
            return true;
        } else {
            // user does not exist
			$stmt->close();
            return false;
        }
    }

    /**
     * Encrypt password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    /**
     * Decrypt password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }

}

?>