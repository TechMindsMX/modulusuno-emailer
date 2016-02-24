<!DOCTYPE html>
<html>
  <head>  <meta charset="utf-8" />
    <title>Notificación de Modulusuno</title>
    <style>
      body {
        font-family: arial;
      }
    </style>
  </head>
  <body>
    <h1>Empresa</h1>
    <hr>
    <p>Este correo es referente a la empresa: <span class="text-blue">${name}</span>, el mensaje es el siguiente: ${message}.</p>
    <br/>
    <p>Referencia: <a href="${url}">${url}</a></p>
    <table border="0" align="left">
      <tbody>
        <tr>
          <th align="left" style="width:30%">
            <img src="http://www.modulusuno.com.s3.amazonaws.com/logo_modulusuno.png" width="60%" align="center">
          </th>
          <th align="left">
            <p><i>Sinceramente,<i></i></i></p><i><i>
            <p><i>Equipo de Modulus UNO</i></p>
            </i></i>
          </th>
        </tr>
      </tbody>
    </table>
  </body>
</html>
