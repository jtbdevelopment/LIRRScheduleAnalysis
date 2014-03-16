class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "peakTrainAnalysis")
        "500"(view: '/error')
    }
}
