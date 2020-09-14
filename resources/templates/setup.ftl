<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ProfileSetup</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<body style="background: Url(/static/background.jpg); height=1vh">
<#--  <ul>
    <#list data.items as item>
    <li>${item}</li>
</#list>
</ul>  -->

<header>
    <nav class="navbar navbar-light navbar-toggleable-sm navbar-expand-md border-bottom box-shadow mb-3 bg-white">
        <div class="container text-center">
            <a href="/" class="navbar-brand mx-auto mx-md-0">KTorAssignment</a>
        </div>
    </nav>
</header>
        <div class="container">
            <div class="row">
                <form method="post" action="/setup" class="bg-white col-8 col-md-4 offset-md-4">
                    <div class="form-group row">
                        <label for="username">Username</label>${locked?then('Y', 'N')}
                        <input type="text" name="username" id="username" class="form-control" value="${user.username}" <#if locked>disabled</#if>>
                    </div>
                    <div class="form-group row">
                        <label for="mobile-number">Mobile Number</label>
                        <input type="tel" name="mobile-number" id="mobile-number" class="form-control" value="${user.mobileNumber}">
                    </div>
                    <div class="row justify-content-center">
                        <button class="btn btn-primary" type="submit">Submit</button>
                    <div>
                </form>
            </div>
        </div>
    </body>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</html>