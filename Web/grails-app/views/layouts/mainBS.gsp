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
    <div class="row header">
        <div class="page-header">
            <h1>Compare My Ride <small>LIRR</small></h1>
        </div>
    </div>
</div>

<div class="container-fluid">

    <div class="row">

        <div class="col-sm-2 col-md-1 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="#">Overview</a></li>
                <li class="active"><a href="#">Peak Ride (Penn)</a></li>
            </ul>
        </div>

        <div class="col-sm-10 col-sm-offset-2 col-md-11 col-md-offset-1 main">
            <g:layoutBody/>
        </div>
    </div>
</div>

<r:layoutResources/>
</body>
</html>