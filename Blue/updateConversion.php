<?php
include 'Conexion.php';
$id = $_POST['id'];
$lastMessage = base64_encode($_POST['lastMessage']);
$mailreceiver = $_POST['mailReceiver'];
$namereceiver = $_POST['nameReceiver'];
$mailsender = $_POST['mailSender'];
$namesender = $_POST['nameSender'];
date_default_timezone_set('America/Mexico_City');
$fecha = date('Y-m-d H:m:s');

$update = "UPDATE conversations SET lastMessage = '".$lastMessage."', mailReceiver = '".$mailreceiver."', receiverName = '".$namereceiver."', mailSender = '".$mailsender."', senderName = '".$namesender."', date='".$fecha."' WHERE conversations.id = '".$id."';"; 

mysqli_query($conexion,$update) or die (mysqli_error());
echo "registrado con éxito";
mysqli_close($conexion);

?>