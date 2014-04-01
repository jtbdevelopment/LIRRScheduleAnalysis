package com.jtbdevelopment.lirr.web

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(OverviewController)
class OverviewControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "setup is redirect to index"() {
        when: "user loads /overview/setup"
        controller.setup()
        then: "should be redirected to index"
        response.redirectedUrl == '/overview/index'
    }

    void "test index"() {
        when: "user loads /overview/index"
        controller.index()
        then: "render the overview"
        view == '/overview/index'
        model.params == [:]
    }
}
