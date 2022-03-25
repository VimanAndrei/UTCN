<html>
<head>

 

<title></title>
</head>

 

<body>

 

<?php

 

require_once('PEAR.php');
$user = 'student';
$pass = 'student123';
$host = 'localhost';
$db_name = 'colocviu_final';
// se stabileşte şirul pentru conexiune universală sau DSN
$dsn= mysqli_connect( $host, $user, $pass, $db_name);
// se verifică dacă a funcţionat conectarea
if ($dsn->connect_error)
{
	die('Eroare la conectare:'. $dsn->connect_error);
}
// se emite interogarea
$query = "select  salariu
from angajati d, certificare e
Where d.salariu >= all(
select  salariu
from angajati a, certificare c
where a.idan = c.idan and c.idav in (select idav from aeronave where numeav like 'AIRBUS%' )) and d.idan=e.idan and e.idav in (select idav from aeronave where numeav like 'AIRBUS%' )";


 
$result = mysqli_query($dsn, $query);

// verifică dacă rezultatul este în regulă
if (!$result)
{
	die('Interogare gresita :'.mysqli_error());
}
// se obţine numărul tuplelor returnate
$num_results = mysqli_num_rows($result);
// se afişează fiecare tuplă returnată

 

  
echo '<table style="width:100%">
  <tr>
    <td>SALARIU</td>
	
  </tr>';
for ($i = 0; $i < $num_results; $i++)
{
  $row = mysqli_fetch_assoc($result);
  

  echo '<td>'.htmlspecialchars(stripslashes($row['salariu'])).'</td></tr>';

  
}
echo '</table>';
 

?>

 <p><a href="interfata.html">REVENIRE</a></p>


</body>
</html>