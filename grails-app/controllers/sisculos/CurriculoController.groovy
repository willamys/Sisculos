package sisculos



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CurriculoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Curriculo.list(params), model:[curriculoInstanceCount: Curriculo.count()]
    }

    def show(Curriculo curriculoInstance) {
        respond curriculoInstance
    }

    def create() {
        respond new Curriculo(params)
    }

    @Transactional
    def save(Curriculo curriculoInstance) {
        if (curriculoInstance == null) {
            notFound()
            return
        }

        if (curriculoInstance.hasErrors()) {
            respond curriculoInstance.errors, view:'create'
            return
        }

        curriculoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'curriculo.label', default: 'Curriculo'), curriculoInstance.id])
                redirect curriculoInstance
            }
            '*' { respond curriculoInstance, [status: CREATED] }
        }
    }

    def edit(Curriculo curriculoInstance) {
        respond curriculoInstance
    }

    @Transactional
    def update(Curriculo curriculoInstance) {
        if (curriculoInstance == null) {
            notFound()
            return
        }

        if (curriculoInstance.hasErrors()) {
            respond curriculoInstance.errors, view:'edit'
            return
        }

        curriculoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Curriculo.label', default: 'Curriculo'), curriculoInstance.id])
                redirect curriculoInstance
            }
            '*'{ respond curriculoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Curriculo curriculoInstance) {

        if (curriculoInstance == null) {
            notFound()
            return
        }

        curriculoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Curriculo.label', default: 'Curriculo'), curriculoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'curriculo.label', default: 'Curriculo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
