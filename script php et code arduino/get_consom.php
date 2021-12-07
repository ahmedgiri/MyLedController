<?php
$serveur="";
$user="root";
$pw="";
$bdd="bd_conso";


$cnconso=new mysqli($serveur,$user,$pw,$bdd);
if($cnconso)
{
$sql="select * from consommation";
$result=mysqli_query($cnconso,$sql);

$response=array();
while($row = mysqli_fetch_assoc($result))
{
	array_push($response, $row);
}
 echo json_encode($response);
}
mysqli_close($cnconso);
?>