
<?php
	date_default_timezone_set('Asia/Kolkata');
	// Create connection
        $conn = mysqli_connect("localhost","ntwonwuc_projects","ntwon1003","ntwonwuc_emogify") or die("Error " . mysqli_error($connection));
        // Check connection
        if ($conn->connect_error) {
           die("Connection failed: " . $conn->connect_error);
        }else{
    	//echo "connetion success";
        }





     $mname=$_REQUEST["mname"];
$mlink=$_REQUEST["mlink"];
$movie=$_REQUEST["movie"];
$singer=$_REQUEST["singer"];
$director=$_REQUEST["director"];
$lang=$_REQUEST["lang"];
$emotion=$_REQUEST["emotion"];

$qry1="select MAX(mid) from tbl_music";
$res=mysqli_query($conn,$qry1);
if($res->num_rows>0)
{
$a=$res->fetch_row();
$b=$a[0]+1;

}
  
        $originalImgName= $_FILES['filename']['name'];
        $tempName= $_FILES['filename']['tmp_name'];
        $folder="uploads/";
        $url = "https://ntwontechnologies.co.in/Ntwon_Projects/Emogify/uploads/".$originalImgName.".mp3"; //update path as per your directory structure 
      //  $cname=$_REQUEST["cname"];
        
       
        
        if(move_uploaded_file($tempName,$folder.$originalImgName.".mp3")){
            
              $qry="insert into tbl_music(eid,mname,mlink,movie,singer,director,language)values('$emotion','$mname','$url','$movie','$singer','$director','$lang')";

              if($conn->query($qry)==true){
    $lastid=$conn->insert_id;
    $qry1="insert into tbl_count(mid,pcount,ucount)values('$lastid','0','0')";
    if($conn->query($qry1)==true){
$a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}
}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}
        	//echo "moved to ".$url;
        }else{
        	echo json_encode(array( "status" => "false","message" => "Failed3!") );
        }
	
  
    



?>