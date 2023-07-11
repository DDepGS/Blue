<?php
include 'Conexion.php';
$lastMessage = base64_encode($_POST['lastMessage']);
$mailreceiver = $_POST['mailReceiver'];
$namereceiver = $_POST['nameReceiver'];
$mailsender = $_POST['mailSender'];
$namesender = $_POST['nameSender'];
date_default_timezone_set('America/Mexico_City');
$fecha = date('Y-m-d H:m:s');

$id = null;


$registro = "INSERT INTO conversations(lastMessage, mailReceiver, receiverName, mailSender, senderName, date) VALUES ('".$lastMessage."', '".$mailreceiver."', '".$namereceiver."', '".$mailsender."', '".$namesender."', '".$fecha."')"; 


if(mysqli_query($conexion,$registro) > 0){
    $consulta = mysqli_query($conexion,"SELECT id FROM conversations WHERE ((mailSender = '".$mailreceiver."') AND (mailReceiver = '".$mailsender."')) OR ((mailSender = '".$mailsender."') AND (mailReceiver = '".$mailreceiver."'))");
    $id = mysqli_fetch_array($consulta);
    echo $id['id'];
}else{
    die (mysqli_error());
} 
mysqli_close($conexion);

?>