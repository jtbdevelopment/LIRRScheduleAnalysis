package com.jtbdevelopment.lirr.web

import org.joda.time.LocalDate

import static org.springframework.http.HttpStatus.*

class ScheduleFacadeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static int nextId = 3
    Map<String, ScheduleFacade> items = ["1": new ScheduleFacade(id: "1", end: LocalDate.now()), "2": new ScheduleFacade(id: "2", end: LocalDate.now())]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond items.values() as List, model: [scheduleFacadeInstanceCount: items.size()]
    }

    def show(String id) {
        respond items.get(id)
    }

    def create() {
        respond new ScheduleFacade(params)
    }

    def save() {
        if (params == null) {
            notFound()
            return
        }

        ScheduleFacade scheduleFacadeInstance = new ScheduleFacade(params)

        if (scheduleFacadeInstance.hasErrors()) {
            respond scheduleFacadeInstance.errors, view: 'create'
            return
        }

        scheduleFacadeInstance.id = nextId.toString()
        nextId++
        items.put(scheduleFacadeInstance.id, scheduleFacadeInstance)
//        scheduleFacadeInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'scheduleFacadeInstance.label', default: 'ScheduleFacade'), scheduleFacadeInstance.id])
                redirect action: "show", method: "GET", id: scheduleFacadeInstance.id
            }
            '*' { respond scheduleFacadeInstance.id, [status: CREATED] }
        }
    }

    def edit(String id) {
        respond items.get(id)
    }

    def update() {
        if (params == null) {
            notFound()
            return
        }

        ScheduleFacade scheduleFacadeInstance = new ScheduleFacade(params)

        if (scheduleFacadeInstance.hasErrors()) {
            respond scheduleFacadeInstance.errors, view: 'edit'
            return
        }
        items.put(scheduleFacadeInstance.id, scheduleFacadeInstance)
//        scheduleFacadeInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ScheduleFacade.label', default: 'ScheduleFacade'), scheduleFacadeInstance.id])
                redirect scheduleFacadeInstance
            }
            '*' { respond scheduleFacadeInstance, [status: OK] }
        }
    }

    def delete() {

        if (params == null) {
            notFound()
            return
        }

        ScheduleFacade scheduleFacadeInstance = new ScheduleFacade(params)
        items.remove(scheduleFacadeInstance.id)
//        scheduleFacadeInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ScheduleFacade.label', default: 'ScheduleFacade'), scheduleFacadeInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'scheduleFacadeInstance.label', default: 'ScheduleFacade'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
