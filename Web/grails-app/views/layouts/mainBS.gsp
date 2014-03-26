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
        <div class="col-sm-8 col-md-8">
            <h1>Compare My Ride <small>LIRR</small></h1>
        </div>

        <div class="col-sm-4 col-md-4">
            <ul class="nav nav-pills" style="margin-top: 20px">
                <li id="overview"><a href="../overview/index">Overview</a></li>
                <li id="peakTrainAnalysis"><a href="../peakTrainAnalysis/index">Penn Peak Analsys</a></li>
                <li id="peakTrainScore"><a href="../peakTrainScore/index">Penn Peak Score</a></li>
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