modules = {
    application {
        defaultBundle false
        resource url: 'js/application.js'
    }

    jquery {
        defaultBundle false
        resource url: '/js/jquery/jquery-1.11.0.min.js'
    }

    bootstrapDashboard {
        defaultBundle false
        dependsOn 'jquery'
        resource url: '/css/bootstrap/bootstrap.css'
        resource url: '/css/datatables/dataTables.bootstrap.css'
//        resource url: '/css/datatables/dataTables.fixedColumns.css'
//        resource url: '/css/datatables/dataTables.fixedHeader.css'
        resource url: '/css/bootstrap/dashboard.css'
        resource url: '/js/bootstrap/bootstrap.min.js'
        resource url: '/js/datatables/jquery.dataTables.min.js'
//       resource url: '/js/datatables/dataTables.fixedColumns.min.js'
//        resource url: '/js/datatables/dataTables.fixedHeader.min.js'
//        resource url: '/js/datatables/dataTables.bootstrap.js'
        resource url: '/js/bootstrap/docs.min.js'
    }
}