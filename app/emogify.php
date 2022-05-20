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

      
		$key=$_REQUEST["key"];

    
     	$qry="";
	$json="";
	$tem="";
     	
//***************************************************************************************************  	
     	
if($key=="login"){
$phone=$_REQUEST["phone"];
$qry="select * from tbl_login where phone='$phone'";
        $result = mysqli_query($conn ,$qry);
    
    if ($result->num_rows >0) 
    {
                 $tem = array('message' =>'success');
                    while($row = $result->fetch_assoc())
                    {
                        $tem['object'] = $row;
                        $json = json_encode($tem);
                    }
                    
                    
                    
        $rat=1;            
$qry11="select ucount from tbl_count ";
        $result11 = mysqli_query($conn ,$qry11);
             if($row11 = $result11->fetch_assoc())
                    {
                         $rat=$rat+$row11['ucount'];
                                $qry22="update tbl_count set ucount='$rat'";

                    }
                    
                    mysqli_query($conn ,$qry22);
        } 
            else
            {
        $tem = array('message' =>'phone number not available our DB');
                $json = json_encode($tem);
                
            }
         echo $json;
}
 //*****************************************************************************************************
 
 if($key=="AddMusicList"){
$mname=$_REQUEST["mname"];
$mlink=$_REQUEST["mlink"];
$movie=$_REQUEST["movie"];
$singer=$_REQUEST["singer"];
$director=$_REQUEST["director"];
$lang=$_REQUEST["lang"];
$emotion=$_REQUEST["emotion"];



$qry="insert into tbl_music(eid,mname,mlink,movie,singer,director,language)values('$emotion','$mname','$mlink','$movie','$singer','$director','$lang')";

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

}
 //*****************************************************************************************************
 
 if($key=="AddEmogy"){
$ename=$_REQUEST["name"];
$eimg=$_REQUEST["phone"];


$qry="insert into tbl_emogy(emogy,emotion_name)values('$eimg','$ename')";

if($conn->query($qry)==true){
$a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}

}
//*****************************************************************************************************
 
 if($key=="updatePlaycount"){
     $rat=1;
     $ra=1;
$mid=$_REQUEST["phone"];
$uid=$_REQUEST["uid"];
$qry1="select pcount from tbl_count where mid='$mid'";
        $result1 = mysqli_query($conn ,$qry1);
             if($row1 = $result1->fetch_assoc())
                    {
                         $rat=$rat+$row1['pcount'];
                        
                                $qry="update tbl_count set pcount='$rat' where mid='$mid'";
                               
                    }
                    
                  



if($conn->query($qry)==true){
$a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}

}
 //*****************************************************************************************************
if($key=="register"){
$name=$_REQUEST["name"];
$phone=$_REQUEST["phone"];


$qry="insert into tbl_register(name,phone)values('$name','$phone')";
// echo $qry;
$qry2="SELECT * FROM tbl_register WHERE phone='$phone'";
// echo $qry2;

                     	    $result = $conn->query($qry2);
                     	    
                     	    if ( $result == TRUE && $result->num_rows == 0 ) 
                     	    {

if($conn->query($qry)==true)
{
$lastid=$conn->insert_id;
$qry1="insert into tbl_login(uid,phone,utype)values('$lastid','$phone','user')";
// echo $qry1;

if($conn->query($qry1)==true){
$a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}
}
else{
$a = array('message' =>'Phone number already exist');
echo json_encode($a);

}

}

// ***********************************************************************************************************************************************
 if($key=="Addrating"){
     $mid=$_REQUEST["mid"];
     $uid=$_REQUEST["uid"];
     $review=$_REQUEST["rev"];
     $rating=$_REQUEST["rat"];
     $count="";
     $rat="0.0";
     $arat="";
     $qry4="select * from tbl_review where uid='$uid' and mid='$mid'";
$res=mysqli_query($conn,$qry4);
if($res->num_rows>0)
{
    
   $a = array('message' =>'You Already rate for this music.');
echo json_encode($a);
  
}else{   
     $qry3="insert into tbl_review(mid,rating,review,uid)values('$mid','$rating','$review','$uid')";

if($conn->query($qry3)==true){
     
      $qry="select count(rating) from tbl_review where mid='$mid'";
     
        $result = mysqli_query($conn ,$qry);
 if ($row = $result->fetch_row()) 
    {
        $count=$row[0];
    }
 
      $qry1="select rating from tbl_review where mid='$mid'";
     
        $result1 = mysqli_query($conn ,$qry1);
             while($row1 = $result1->fetch_assoc())
                    {
                        
                         $rat=$rat+$row1['rating'];
                        
                    }
                 
        
        $arat=$rat/$count;
        
         $qry2="update tbl_music set arating='$arat' where mid='$mid'";

        
        if($conn->query($qry2)==true){
        $a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}
       
}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}
}


 }
    
//************************************************************************************************************************************************

if($key=="getEmotion_AllMusics"){
      $lan=$_REQUEST["lan"];
      $eid=$_REQUEST["eid"];
$qry="select * from tbl_music m,tbl_emogy e ,tbl_count c where e.eid=m.eid and c.mid=m.mid and m.language='$lan' and m.eid='$eid' ORDER BY arating DESC";
$res=mysqli_query($conn,$qry);
if($res->num_rows>0)
{
    
 $tem = array('message' =>'success');
                    while($row[] = $res->fetch_assoc())
                    {
                        $tem['data'] = $row;
                      
                    }
                      $json = json_encode($tem);
                      
}
else{
    $tem = array('message' =>'No Music List Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}

//************************************************************************************************************************************************
if($key=="getrec_AllMusics"){
      $lan=$_REQUEST["lan"];
$qry="select * from tbl_music m,tbl_emogy e ,tbl_count c where e.eid=m.eid and c.mid=m.mid and m.arating>3 and m.language='$lan'";
$res=mysqli_query($conn,$qry);
if($res->num_rows>0)
{
    
 $tem = array('message' =>'success');
                    while($row[] = $res->fetch_assoc())
                    {
                        $tem['data'] = $row;
                      
                    }
                      $json = json_encode($tem);
                      
}
else{
    $tem = array('message' =>'No Music List Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}

// ***********************************************************************************************************************************************

if($key=="DeleteEmoji"){
$mid=$_REQUEST["phone"];



$qry="delete from tbl_music where eid='$mid'";
$qry2="delete from tbl_emogy where eid='$mid'";

if($conn->query($qry)==true && $conn->query($qry2)==true){
$a = array('status' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('status' =>'failed');
echo json_encode($a);
}

}
//**************************************************************************************************************************************************
if($key=="getspinner"){
$qry="select * from tbl_emogy";
$res=mysqli_query($conn,$qry);
if($res->num_rows>0)
{
 $tem = array('message' =>'success');
                    while($row[] = $res->fetch_assoc())
                    {
                        $tem['data'] = $row;
                      
                    }
                      $json = json_encode($tem);
                      
}
else{
    $tem = array('message' =>'No Emotions available.Please Add an Emoji first');
     $json = json_encode($tem);
                     
}
echo $json;
}

//*************************************************************************************************************************************************
if($key=="AllEmogies"){
$qry="select * from tbl_emogy";
$res=mysqli_query($conn,$qry);
if($res->num_rows>0)
{
 $tem = array('message' =>'success');
                    while($row[] = $res->fetch_assoc())
                    {
                        $tem['data'] = $row;
                      
                    }
                      $json = json_encode($tem);
                      
}
else{
    $tem = array('message' =>'No emojis available');
     $json = json_encode($tem);
                     
}
echo $json;
}
//**********************************************************************************************************************************************

if($key=="ViewReviews"){
    $mid=$_REQUEST["phone"];
$qry="select * from tbl_review r,tbl_register c where r.mid='$mid' and r.uid=c.uid";
$res=mysqli_query($conn,$qry);
if($res->num_rows>0)
{
 $tem = array('message' =>'success');
                    while($row[] = $res->fetch_assoc())
                    {
                        $tem['data'] = $row;
                      
                    }
                      $json = json_encode($tem);
                      
}
else{
    $tem = array('message' =>'No reviews Available for this music');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*************************************************************************************************************************************************
if($key=="AllMusics"){
$qry="select * from tbl_music m,tbl_emogy e ,tbl_count c where e.eid=m.eid and c.mid=m.mid";
$res=mysqli_query($conn,$qry);
if($res->num_rows>0)
{
 $tem = array('message' =>'success');
                    while($row[] = $res->fetch_assoc())
                    {
                        $tem['data'] = $row;
                      
                    }
                      $json = json_encode($tem);
                      
}
else{
    $tem = array('message' =>'No Music List Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*******************************************************************************************************
	?>