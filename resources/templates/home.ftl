<#--  @ftlvariable name="data" type="com.test.ktor.IndexData"  -->
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Kotlin Assignment</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body style="background: Url(/static/background.jpg); height=1vh">

        <header>
            <nav class="navbar navbar-light navbar-toggleable-sm navbar-expand-md border-bottom box-shadow mb-3 bg-white">
                <div class="container text-center">
                    <a href="/" class="navbar-brand mx-auto mx-md-0">KTorAssignment</a>
                </div>
                <a href="/logout">Sign Out</a>

            </nav>
        </header>
        <div class="container bg-white">
            <div class="d-inline">
                <a href="/setup">Profile</a>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="card p-0 col-8 offset-2 col-sm-7 col-md-4 offset-md-8 bg-white justify-content-center">
                    <div class="card-header">
                       <div class="card-title">
                            ${user.name}
                       </div>
                    </div>
                    <div class="card-body">
                        <img src="${user.profilePicture}" width="100%"/>
                    </div>   
                </div>               
            </div>
        </div>
    </body>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</html>
