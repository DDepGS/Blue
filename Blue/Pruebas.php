<?php
   include 'Conexion.php';
	require 'PHPMailer/src/PHPMailer.php';
   require 'PHPMailer/src/SMTP.php';
   require 'PHPMailer/src/Exception.php';

   	$correo=$_GET['correo'];

	$usuariop = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Correo= '".$correo."'");
	$usuarioe = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE Correo= '".$correo."'");

	if($rowpusuario = mysqli_fetch_array($usuariop))
	{
		$mail = new PHPMailer\PHPMailer\PHPMailer();

		$mail->CharSet = 'UTF-8';
		$mail->IsHTML(true); 

		$mail->isSMTP();
		$mail->SMTPAuth = true;
		$mail->SMTPSecure = 'tls';
		$mail->Host = 'smtp.gmail.com';
		$mail->Port = 587;
		
		$mail->Username = 'dgutierrezs623@gmail.com'; //Correo de donde enviaremos los correos
		$mail->Password = 'knpjqxvhievzxhty'; // Password de la cuenta de envío
		
		$mail->setFrom('dgutierrezs623@gmail.com', 'Emisor');
		$mail->addAddress($correo, 'Receptor'); //Correo receptor
		
		$cadena = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		$longitudCadena=strlen($cadena);    
		$pass = "";
		$longitudPass=8;    
		for($i=1 ; $i<=$longitudPass ; $i++){
			$pos=rand(0,$longitudCadena-1);     
			$pass .= substr($cadena,$pos,1);
		}

		$registro="UPDATE u_paciente SET Contrasena='".$pass."' WHERE Correo='".$correo."'";
        mysqli_query($conexion,$registro) or die (mysqli_error());
        mysqli_close($conexion);

		$mail->Subject = 'Recuperar contraseña';
		$mail->Body    = '<p>Hemos creado una contraseña temporal: </p>'.$pass. '<p>No olvides cambiarla.</p>';
		
		if($mail->send()) 
		{
			echo 'Correo enviado';
		} 
		else 
		{
			echo 'Error al enviar correo';
		}
	}
	else if($roweusuario = mysqli_fetch_array($usuarioe))
	{
		$mail = new PHPMailer\PHPMailer\PHPMailer();
	
		$mail->isSMTP();
		$mail->SMTPAuth = true;
		$mail->SMTPSecure = 'tls';
		$mail->Host = 'smtp.gmail.com';
		$mail->Port = 587;
		
		$mail->Username = 'dgutierrezs623@gmail.com'; //Correo de donde enviaremos los correos
		$mail->Password = 'knpjqxvhievzxhty'; // Password de la cuenta de envío
		
		$mail->setFrom('dgutierrezs623@gmail.com', 'Emisor');
		$mail->addAddress($correo, 'Receptor'); //Correo receptor
		
		$cadena = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		$longitudCadena=strlen($cadena);    
		$pass = "";
		$longitudPass=8;    
		for($i=1 ; $i<=$longitudPass ; $i++){
			$pos=rand(0,$longitudCadena-1);     
			$pass .= substr($cadena,$pos,1);
		}

		$registro="UPDATE u_especialista SET Contrasena='".$pass."' WHERE Correo='".$correo."'";
        mysqli_query($conexion,$registro) or die (mysqli_error());
        mysqli_close($conexion);

		$mail->Subject = 'Recuperar contraseña';
		$mail->Body    = 'Contenido del correo '.$pass;
		
		if($mail->send()) 
		{
			echo 'Correo enviado';
		} 
		else 
		{
			echo 'Error al enviar correo';
		}
	}
	else
	{
		echo "Error en el correo";
	}

?>