/*==============================================================*/
/*	DB_Functions.php						                    */
/*	Grapp					  							        */
/*	by Hein Dauven							                    */
/*==============================================================*/
// Database handler class; contains code and queries to insert, 
// update and retrieve data from the database.
<?php
error_reporting(E_ALL ^ E_DEPRECATED);
class DB_Functions {

    private $db;

    function __construct() { // constructor
        require_once 'DB_Connect.php';
        // connect to database
        $this->db = new DB_Connect();
	$this->db->connect();
    }

    function __destruct() { // destructor
        
    }

    /**
     * Store user details
     */
    public function storeUser($name, $email, $password) {
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysqli_query($this->db->con,"INSERT INTO users(user_name, user_email, user_password, salt) VALUES('$name', '$email', '$encrypted_password', '$salt')") or die(mysqli_error($this->db));
        // check for result
        if ($result) {
            // getting the details
            $uid = mysqli_insert_id($this->db->con); // last inserted id
            $result = mysqli_query($this->db->con,"SELECT * FROM users WHERE user_id = $uid");
            // return details
            return mysqli_fetch_array($result);
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
        $result = mysqli_query($this->db->con,"SELECT * FROM users WHERE user_email = '$email'") or die(mysqli_connect_errno());
        // check for result 
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['user_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password
            if ($encrypted_password == $hash) {
                return $result;
            }
        } else {
            return false;
        }
    }

    /**
     * Check if the user exists or not
     */
    public function doesUserExist($email) {
        $result = mysqli_query($this->db->con,"SELECT user_email from users WHERE user_email = '$email'");
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            // user exists
            return true;
        } else {
            // user does not exist
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