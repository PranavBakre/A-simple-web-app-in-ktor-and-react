<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>ProfileSetup</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
        integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
        <script>
        function deleteAddress(id) {
            console.log("DEL")
            fetch(`/address/`+id,{
                method:"delete"
            })
            location.reload()
        }
        </script>
    
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
        <div class="justify-content-center row form-group mx-auto">
            Profile
        </div>

        <hr />
        <div class="row justify-content-center">

            <div class="col-10 ">
                <form method="post" action="/setup" class="mt-3 container" id="formProfile">

                    <div class="row justify-content-center">
                        <div class="form-group col-12 col-md-5  justify-content-center">
                            <label for="name">Name</label>
                            <input type="text" id="name" class="form-control" value="${user.name}" disabled>
                        </div>
                        <div class="form-group col-12 col-md-5 offset-md-1  justify-content-center">
                            <label for="mobile-number">Email</label>
                            <input type="email" id="mobile-number" class="form-control " value="${user.email}" disabled>
                        </div>
                    </div>
                    <div class="row justify-content-center">
                        <div class="form-group col-12 col-md-5  justify-content-center">
                            <label for="username">Username</label>
                            <input type="text" name="username" id="username" class="form-control userProfile"
                                value="${user.username}" <#if locked>disabled</#if>>
                        </div>
                        <div class="form-group col-12 col-md-5 offset-md-1  justify-content-center">
                            <label for="mobile-number">Mobile Number</label>
                            <input type="tel" name="mobile-number" id="mobile-number" class="form-control userProfile"
                                value="${user.mobileNumber}">
                        </div>
                    </div>
                    <div class="row justify-content-center  mb-2 d-none" id="profileSubmit">
                        <button class="btn btn-primary" type="submit">Submit</button>
                    </div>
                </form>


            </div>
        </div>
        <div class="row justify-content-center">
            Address
        </div>
        <hr />
        <div class="row justify-content-start mx-5">
            <#if Addresses??>
                <#list Addresses as address>
                    <div class="col-12 col-md-5 card">
                        ${address.title}
                        <hr />
                        <div>
                            ${address.line1}
                        </div>
                        <div>
                            ${address.line2}
                        </div>
                        <div>${address.locality}</div>
                        <div>${address.city}, ${address.state}, PinCode:${address.pincode} <a onclick="deleteAddress(${address.id})"><i class="fa fa-trash"></i></a></div>
                    </div>
                </#list>
            </#if>
        </div>
        <a onclick="addAddress()"><i class="fa fa-plus"></i></a>
        <div class="row justify-content-center">
            <form id="addressForm" class="d-none container">
                <div class="form-group col-10 offset-1 col-md-8 offset-md-2">
                    <label>Title</label>
                    <input type="text" name="title" class="form-control" />
                </div>
                <div class="form-group col-10 offset-1 col-md-8 offset-md-2">
                    <label>Line 1</label>
                    <input type="text" name="line1" class="form-control" />
                </div>
                <div class="form-group col-10 offset-1 col-md-8 offset-md-2">
                    <label>Line 2</label>
                    <input type="text" name="line2" class="form-control" />
                </div>
                <div class="form-group d-inline-flex col-10 offset-1 col-md-8 offset-md-2">
                    <div class="col-6 col-sm-4 pl-0">
                        <label>Locality</label>
                        <input type="text" name="locality" class="form-control" />
                    </div>
                    <div class="col-6 col-sm-4">
                        <label>City</label>
                        <input type="text" name="city" class="form-control" />
                    </div>
                    <div class="col-12 col-sm-4 pr-0">
                        <label>State</label>
                        <input type="text" name="state" class="form-control" />
                    </div>
                </div>
                <div class="form-group col-6 offset-2 col-md-3">
                    <label>Pincode</label>
                    <input type="text" name="pincode" class="form-control" />
                </div>
                <div class="col-10 offset-2">
                    <button class="btn btn-primary" type="submit">Submit</button>
                </div>
            </form>
        </div>
    </div>
</body>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script>
        $(".userProfile").on('change', () => {
            $("#profileSubmit").removeClass("d-none")
        })
    
        document.getElementById("formProfile").addEventListener("submit", (e) => {
            event.preventDefault()
            var formData = new FormData(document.getElementById("formProfile"))
            fetch("/setup", {
                method: "post",
    
                body: formData
    
            })
            location.reload()
        })
    
        function addAddress() {
            $("#addressForm").removeClass("d-none")
        }

    
        document.getElementById("addressForm").addEventListener("submit", (e) => {
            event.preventDefault()
            var formData = new FormData(document.getElementById("addressForm"))
            fetch("/address", {
                method: "post",
    
                body: formData
    
            })
            location.reload()
        })
    </script>
</html>