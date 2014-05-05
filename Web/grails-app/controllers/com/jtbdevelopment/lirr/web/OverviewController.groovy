package com.jtbdevelopment.LIRR.web

class OverviewController {
    def setup() {
        redirect(action: "index");
    }

    def index() {
        render view: 'index'
    }
}
