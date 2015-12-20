<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Notificación</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <!-- Custom styles for this template -->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
      body
      {
        padding-top: 50px;
      }

      .starter-template
      {
        padding: 40px 15px;
        text-align: center;
      }

      .text-blue
      {
        color: #5bb2ef;
      }
    </style>
  </head>
  <body>
    <table align="left">
      <thead>
        <tr>
          <th>
            <h1>Notificaci\u00f3n Integradora</h1>
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
                      <h1>Informaci\u00f3n Bancaria</h1>
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
                      <h3>Mediante Dep\u00f3sito</h3>
                      <p>${bank}</p>
                      <p>${accountBank}</p>
                    </td>
                  </tr>
                </tbody>
              </table>
            <br/>
            <p>Confirmaci\u00f3n: <a href="${url}">${url}</a></p>
          </td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
