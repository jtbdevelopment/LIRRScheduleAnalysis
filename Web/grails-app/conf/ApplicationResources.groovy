modules = {
    application {
        resource url: 'js/application.js'
    }

    jquery {
        resource url: '/js/jquery/jquery-1.11.0.min.js'
    }

    bootstrapDashboard {
        dependsOn 'jquery'
        resource url: '/css/bootstrap/bootstrap.min.css'
        resource url: '/css/datatables/dataTables.bootstrap.css'
        resource url: '/js/bootstrap/bootstrap.min.js'
        resource url: '/js/datatables/jquery.dataTables.min.js'
        resource url: '/js/bootstrap/docs.min.js'
        resource url: '/js/highcharts/highcharts.js'
        resource url: '/css/bootstrap-slider/bootstrap-slider.min.css'
        resource url: '/js/bootstrap-slider/bootstrap-slider.min.js'
        resource url: '/css/bootstrap/dashboard.css', bundle: 'dashboard'
        resource url: '/js/analysis.js', bundle: 'analysis'
    }
}