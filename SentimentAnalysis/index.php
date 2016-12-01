<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Code Mix : Sentiment Analysis</title>
</head>
<body style="background: none repeat scroll 0% 0% rgb(0, 49, 86);" >
	<h1 align="center">CodeMixed Opinion Miner</h1>
<h1 align="center">Under Construction!!</h1>
	<hr color="666666" size="4" width="100%">

	<form name="form1" id="form1" method="post" action="">
            <table width="100%">
            <tbody>
            <tr>
                    <td align="center">Enter Text:</td>
            </tr>
            <tr>
            </tr>
            </tbody></table>
            <p align="center">
                    <textarea name="input" cols="46" rows="5" style="font-size:16pt;"></textarea><br><br>
                    <input name="submit" value="Submit" type="submit"><br>
            </p>
	</form>


	<hr color="666666" size="4" width="100%">

<?php
   if( isset($_POST) ) {
         //Let's assume sanitation and validation on input is done in the function
         //authenticateUser($_POST['txtUsername'], $_POST['txtPassword']);
        $query=$_POST['input'];
        if($query==NULL || $query==""){
        }
        else{
            $cmd="/var/www/SentiAna/run.sh \"$query\" 2>&1";
            //$cmd='/var/www/SentiAna/runClassifierForClient.sh 2>&1';
           
            while (@ ob_end_flush()); // end all output buffers if any
            $proc = popen($cmd, 'r');
            echo '<pre>';
            while (!feof($proc))
            {
                echo fread($proc, 4096);
                //echo ".";
                @ flush();
            }
            echo '</pre>';
            echo '<h4>';
            echo "Input Sentence: ";
            echo $query;
            echo '</h4>';
            echo '<h3>';
            echo shell_exec("cat /var/www/SentiAna/Temp/out.txt 2>&1");
            echo '</h3>';
            //echo 'Command is '.$cmd;
        }
   }
?>
