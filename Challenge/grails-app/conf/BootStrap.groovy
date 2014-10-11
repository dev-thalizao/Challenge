import security.*

class BootStrap {

	def init = { servletContext ->

		//Se não tiver nenhum usuário, cria o admin
		if(UserRole.count() == 0){
			def user = new User(username: "root", password: "root").save(flush: true)
			def roleAdmin = new Role(authority: "ROLE_ADMIN", true).save(flush: true)
			def roleUser = 	new Role(authority: "ROLE_USER", true).save(flush: true)
			def roleFacebook = new Role(authority: "ROLE_FACEBOOK", true).save(flush: true)
			def userFinal = new UserRole(user: user, role:role).save(flush: true)
		}
	}
	def destroy = {
	}
}
