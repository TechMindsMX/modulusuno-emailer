<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>Notificación de Modulusuno</title>
    <style>
    body {
      font-family: arial;
    }
    </style>
  </head>
  <body>
    <img src="http://techminds.com.mx.s3.amazonaws.com/img/logoiecce-small.png">
    <h1>Empresa Integrada</h1>
    <hr>
    <p>Este correo es referente a la empresa: <span class="text-blue">${name}</span>, el mensaje es el siguiente: ${message}.</p>
    <br/>
    <table align="left" border="1">
    <tr>
      <th colspan="2">
      <h1>Información Bancaria</h1>
      </th>
    </tr>
    <tr>
      <td>
      <h3>Mediante Transferencia</h3>
      <p>${account}</p>
      </td>
      <td>
        <h3>Mediante Dep&oacute;sito</h3>
        <p>${bank}</p>
        <p>${accountBank}</p>
      </td>
    </tr>
    </table>
    <br/><br/><br/><br/><br/><br/>
    <br/><br/><br/><br/><br/><br/>
    <br/><br/>
    <p>Confirmación: <a href="${url}">${url}</a></p>
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
