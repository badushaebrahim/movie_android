<?php
	date_default_timezone_set('Asia/Kolkata');
	// Create connection
        $conn = mysqli_connect("localhost","root","","moviez") or die("Error " . mysqli_error($connection));
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
                    
                    
     }
            else
            {
        $tem = array('message' =>'phone number not available our DB');
                $json = json_encode($tem);
                
            }
         echo $json;
}
 //*****************************************************************************************************
 
 if($key=="addtheaters"){
$tname=$_REQUEST["tname"];
$scap=$_REQUEST["scap"];
$tadd=$_REQUEST["tadd"];
$tphone=$_REQUEST["tphone"];

$qry5="select * from tbl_login where phone='$tphone'";
        $result = mysqli_query($conn ,$qry5);
    
    if ($result->num_rows >0) 
    {
$a = array('message' =>'phone number already registered');
echo json_encode($a);
}else{

$qry="insert into tbl_theater(tname,scap,taddress,tphone)values('$tname','$scap','$tadd','$tphone')";

if($conn->query($qry)==true){
  $lastid=$conn->insert_id;
$qry1="insert into tbl_login(uid,phone,utype)values('$lastid','$tphone','theater')";
// echo $qry1;

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
}
 //*****************************************************************************************************
if($key=="addfare"){
$mid=$_REQUEST["mid"];
$time=$_REQUEST["time"];
$uid=$_REQUEST["uid"];

$qry5="select * from tbl_price where tid='$uid' and mid='$mid'";
        $result = mysqli_query($conn ,$qry5);
    
    if ($result->num_rows >0) 
    {
$a = array('message' =>'Mocie price already added');
echo json_encode($a);
}else{
$qry="insert into tbl_price(tid,mid,price)values('$uid','$mid','$time')";

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
}
//***********************************************************
if($key=="addshowtime"){
$mid=$_REQUEST["mid"];
$time=$_REQUEST["time"];
$uid=$_REQUEST["uid"];


$qry="insert into tbl_show(eid,time,uid)values('$mid','$time','$uid')";

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
 //**************************************************************

if($key=="assign"){
$mval=$_REQUEST["mval"];
$tval=$_REQUEST["tval"];

$qry2="SELECT * FROM tbl_theatermovie WHERE tid='$tval' and eid='$mval'";
// echo $qry2;

                            $result = $conn->query($qry2);
                            
                            if ( $result == TRUE && $result->num_rows == 0 ) 
                            {
$qry="insert into tbl_theatermovie(tid,eid)values('$tval','$mval')";

if($conn->query($qry)==true){
$a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}
}else{
    $a = array('message' =>'This movie already Assigned for this theater');
echo json_encode($a);
}

}
//*****************************************************************
 if($key=="addmovie"){
$ename=$_REQUEST["name"];
$director=$_REQUEST["director"];
$star=$_REQUEST["star"];
$eimg=$_REQUEST["phone"];
//$tid=$_REQUEST["tid"];
$lang=$_REQUEST["lang"];
$qry2="SELECT * FROM tbl_movies WHERE mname='$ename'";
// echo $qry2;

                            $result = $conn->query($qry2);
                            
                            if ( $result == TRUE && $result->num_rows == 0 ) 
                            {

$qry="insert into tbl_movies(mimg,mname,director,star,language)values('$eimg','$ename','$director','$star','$lang')";

if($conn->query($qry)==true){
$a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}
}else{
    $a = array('message' =>'A movie added with this same name');
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
if($key=="bookticket"){
$mname=$_REQUEST["mname"];
$tname=$_REQUEST["tname"];
$time=$_REQUEST["time"];
$bdate=$_REQUEST["bdate"];
$num=$_REQUEST["numticket"];
$amount=$_REQUEST["amount"];
$uid=$_REQUEST["uid"];
$tid=$_REQUEST["tid"];
$mpic=$_REQUEST["mpic"];
$qr=$_REQUEST["qr"];
$qry="insert into tbl_ticket(mname,tname,time,bdate,numticket,amount,uid,tid,mpic,qrcode)values('$mname','$tname','$time','$bdate','$num','$amount','$uid','$tid','$mpic','$qr')";

if($conn->query($qry)==true)
{
$a = array('message' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('message' =>'failed');
echo json_encode($a);
}


}
//***********************************************************************
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
  if($key=="getmybook1"){
   
    $uid=$_REQUEST["uid"];
$qry="select * from tbl_ticket  where tid='$uid'";
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
    $tem = array('message' =>'No show time  Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}
 //*************************************************
 if($key=="getmybook"){
   
    $uid=$_REQUEST["uid"];
$qry="select * from tbl_ticket  where uid='$uid'";
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
    $tem = array('message' =>'No show time  Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*/*************************************************
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

// ******************************************************************************************************************

if($key=="cancelticket"){
$mid=$_REQUEST["phone"];



$qry="delete from tbl_ticket where ticket_id='$mid'";

if($conn->query($qry)==true){
$a = array('status' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('status' =>'failed');
echo json_encode($a);
}

}
//*******************************************************

if($key=="deleteshow"){
$mid=$_REQUEST["phone"];

$qry="delete from tbl_show where sid='$mid'";

if($conn->query($qry)==true){
$a = array('status' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('status' =>'failed');
echo json_encode($a);
}

}
//********************************************************
if($key=="deleteprice"){
$mid=$_REQUEST["phone"];



$qry="delete from tbl_price where pid='$mid'";

if($conn->query($qry)==true){
$a = array('status' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('status' =>'failed');
echo json_encode($a);
}

}
//*******************************************************
if($key=="deletetheater"){
$mid=$_REQUEST["phone"];



$qry="delete from tbl_theater where mid='$mid'";

if($conn->query($qry)==true){
$a = array('status' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('status' =>'failed');
echo json_encode($a);
}

}
//*****************************

if($key=="deletemovie"){
$mid=$_REQUEST["phone"];



$qry="delete from tbl_movies where eid='$mid'";

if($conn->query($qry)==true){
$a = array('status' =>'success');
//echo "here";
echo json_encode($a);
}
else{
$a = array('status' =>'failed');
echo json_encode($a);
}

}
//*******************************************************************************************************************

if($key=="getspinner1"){
$qry="select * from tbl_movies";
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
    $tem = array('message' =>'No Movies available.Please Add an Emoji first');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*******************************
if($key=="getspinner"){
$qry="select * from tbl_theater";
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
    $tem = array('message' =>'No Theaters available.');
     $json = json_encode($tem);
                     
}
echo $json;
}

//*******************************************************************************************************************
if($key=="gettime"){
    $lang=$_REQUEST["uid"];
$qry="select * from tbl_show p,tbl_movies m where p.eid=m.eid and p.uid='$lang'";
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
    $tem = array('message' =>'No Time available available');
     $json = json_encode($tem);
                     
}
echo $json;
}
//***********************************
if($key=="getfare"){
    $lang=$_REQUEST["uid"];
$qry="select * from tbl_price p,tbl_movies m where p.mid=m.eid and p.tid='$lang'";
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
    $tem = array('message' =>'No fare available');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*******************************************
if($key=="viewmovie1"){
    $lang=$_REQUEST["lan"];
$qry="select * from tbl_movies where language='$lang'";
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
    $tem = array('message' =>'No movies available');
     $json = json_encode($tem);
                     
}
echo $json;
}
//******************************
if($key=="viewourmovie"){
    $uid=$_REQUEST["uid"];
$qry="select * from tbl_movies m,tbl_theatermovie t  where m.eid=t.eid and t.tid='$uid'";
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
    $tem = array('message' =>'No Movies available');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*******************************

if($key=="viewmovie"){
$qry="select * from tbl_movies";
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
    $tem = array('message' =>'No movies available');
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
//******************************************************************************************************
if($key=="alltheaters1"){
    $mid=$_REQUEST["uid"];
$qry="select * from tbl_theater t,tbl_theatermovie t1 where t.mid=t1.tid and t1.eid='$mid'";
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
    $tem = array('message' =>'No Theaters List Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}
//********************************************
if($key=="getprice"){
    $tid=$_REQUEST["tid"];
    $mid=$_REQUEST["eid"];
$qry="select * from tbl_price  where mid='$mid' and tid='$tid'";
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
    $tem = array('message' =>'No price List Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*********************************************
if($key=="allshows"){
    $tid=$_REQUEST["tid"];
    $mid=$_REQUEST["eid"];
$qry="select * from tbl_show  where eid='$mid' and uid='$tid'";
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
    $tem = array('message' =>'No show time  Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*******************************************
if($key=="alltheaters"){
$qry="select * from tbl_theater";
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
    $tem = array('message' =>'No theater Available now');
     $json = json_encode($tem);
                     
}
echo $json;
}
//*******************************************************************************************************
    ?>