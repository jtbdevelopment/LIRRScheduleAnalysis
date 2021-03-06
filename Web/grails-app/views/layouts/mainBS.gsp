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
        <div class="col-sm-9 col-md-9">
            <h1>Compare My Ride <small>LIRR (Prototype) ${grailsApplication.metadata.'app.version'}</small></h1>
        </div>

        <div class="col-sm-3 col-md-3">
            <ul class="nav nav-pills" style="margin-top: 20px">
                <li id="overview"><a href="${createLink(controller: 'Overview', action: 'index')}">Overview</a></li>
                <li id="peakTrainAnalysis"><a
                        href="${createLink(controller: 'PeakTrainAnalysis', action: 'index')}">Penn Peak Analsys</a>
                </li>
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