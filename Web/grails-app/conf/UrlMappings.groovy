class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "peakTrainAnalysis", action: "setup")
        "500"(view: '/error')
    }
}
