<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Notificaci&oacute;n</title>
  </head>
  <body>
    <table align="left">
      <thead>
        <tr>
          <th>
            <h1>Notificaci&oacute;n Integradora</h1>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <HR width=100% align="center">
            <p>Este correo es referente a la empresa: <span class="text-blue">${name}</span>, el mensaje es el siguiente: ${message}.
            </p>
            <br/>
            <table align="left" border="1">
              <thead>
                <tr>
                  <th>
                    <h1>Informaci&oacute;n Bancaria</h1>
                  </th>
                </tr>
              </thead>
              <tbody>
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
              </tbody>
            </table>
            <br/>
            <p>Confirmaci&oacute;n: <a href="${url}">${url}</a></p>
          </td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
