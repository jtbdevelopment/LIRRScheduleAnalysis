<form class="form-inline top-input" action="javascript:showAnalysis()">
    <div class="form-group">
        <label for="analysis">When:</label>
        <g:select class="form-control" name="analysis" from="${stringInstanceMap.entrySet()}" optionKey="key"
                  optionValue="value"/>
        <button id="showMe" data-loading-text="Loading.." class="btn btn-default">Show Me</button>
    </div>
</form>

<div id="report" class="container-fluid">
</div>

