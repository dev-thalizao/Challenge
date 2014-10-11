package challenge


import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class ChallengeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Challenge.list(params), model:[challengeInstanceCount: Challenge.count()]
    }

    def show(Challenge challengeInstance) {
        respond challengeInstance
    }

    def create() {
        respond new Challenge(params)
    }

    @Transactional
    def save(Challenge challengeInstance) {
        if (challengeInstance == null) {
            notFound()
            return
        }

        if (challengeInstance.hasErrors()) {
            respond challengeInstance.errors, view:'create'
            return
        }

        challengeInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'challenge.label', default: 'Challenge'), challengeInstance.id])
                redirect challengeInstance
            }
            '*' { respond challengeInstance, [status: CREATED] }
        }
    }

    def edit(Challenge challengeInstance) {
        respond challengeInstance
    }

    @Transactional
    def update(Challenge challengeInstance) {
        if (challengeInstance == null) {
            notFound()
            return
        }

        if (challengeInstance.hasErrors()) {
            respond challengeInstance.errors, view:'edit'
            return
        }

        challengeInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Challenge.label', default: 'Challenge'), challengeInstance.id])
                redirect challengeInstance
            }
            '*'{ respond challengeInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Challenge challengeInstance) {

        if (challengeInstance == null) {
            notFound()
            return
        }

        challengeInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Challenge.label', default: 'Challenge'), challengeInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'challenge.label', default: 'Challenge'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
