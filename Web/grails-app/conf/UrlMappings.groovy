class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "overview", action: "setup")
        "500"(view: '/error')
    }
}
