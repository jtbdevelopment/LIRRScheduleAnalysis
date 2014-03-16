<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><g:layoutTitle default="LIRR - Compare My Ride"/></title>
    <r:require modules="bootstrapDashboard"/>
    <g:layoutTitle/>
    <r:layoutResources/>
</head>

<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-md-10">
            <h1>Compare My Ride <small>LIRR</small></h1>
        </div>

        <div class="col-sm-3 col-md-2">
            <ul class="nav nav-pills" style="margin-top: 20px">
                <li><a href="#">Overview</a></li>
                <li class="active"><a href="#">Peak Ride (Penn)</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="col-sm-12 col-md-12 main">
        <g:layoutBody/>
    </div>
</div>

<r:layoutResources/>
</body>
</html>