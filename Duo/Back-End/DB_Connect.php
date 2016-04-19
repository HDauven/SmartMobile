/*==============================================================*/
/*	DB_Connect.php							                    */
/*	Grapp					  							        */
/*	by Hein Dauven							                    */
/*==============================================================*/
<?php
class DB_Connect {
    public $conn; // constructor
    
    public function connect() { // Connecting to database
		// include the configuration file
        require_once 'db_config.php';
		
        // connect to mysql
        $this->conn = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
        
        return $this->conn; // return database handler
    }

    public function close() { // Close the database connection
        mysqli_close($this->conn);
    }
}
?>