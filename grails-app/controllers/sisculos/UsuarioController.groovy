package sisculos

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UsuarioController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def login = {}

	def initial = {}

	def authenticate = {
		//consulta se o login e senha existem no banco de dados
		//def usuario_login =  Usuario.findByLoginAndSenha(params.login, params.senha)
		//verifica se houve retorno na consulta para autenticar no sistema
		//Usuario permissao = Usuario.findByLogin(usuario_login.login)

		Usuario usuario_login =  Usuario.findByLoginAndSenha(params.login, params.senha)

		if(usuario_login!= null){
			session.user = usuario_login
			flash.message = "Oi, ${usuario_login.login}!"
			// redirect(action:"login")
			redirect(controller:'usuario',action:'initial')
		}else{
			flash.message = "Desculpe, ${params.login}. Tente Novamente."
			redirect(controller:'usuario',action:'login')
		}
	}

	def logout = {
		if(session.user == null){ //verificar se a sessão já é nula e não mostra o nome do ultimo login
			flash.message = "Desculpe, pagina inacessivel. Tente Novamente."
			session.user = null
			redirect(controller:'usuario',action:'login')
		}else{	//se a sessão não for nula, mostra o nome do último login
			flash.message = "Tchau, ${session.user.login}!"
			session.user = null
			redirect(controller:'usuario',action:'login')
		}
	}

	def index(Integer max) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0 ){
				params.max = Math.min(max ?: 10, 100)
				respond Usuario.list(params), model:[usuarioInstanceCount: Usuario.count()]
			}else if(usuario_login.permissao == 1 || usuario_login.permissao == 2){
				respond Usuario.findAllWhere(login: usuario_login.login), model:[usuarioInstanceCount: Usuario.count()]
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'usuario',action:'login')
		}
	}

	def show(Usuario usuarioInstance) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0){
				respond usuarioInstance
			}else if((usuario_login.permissao == 1 || usuario_login.permissao == 2) && (usuario_login.id == usuarioInstance.id)){
				respond usuarioInstance
			}else{
				flash.message = "Desculpe, o usuario nao tem permissao."
				redirect(controller:'usuario',action:'initial')
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'usuario',action:'login')
		}
	}

	def create() {
		Usuario usuario_login = session.user
		if(usuario_login != null && usuario_login.permissao == 0){
			respond new Usuario(params)
		}else{
			flash.message = "Desculpe, o usuario nao tem permissao."
			redirect(controller:'usuario',action:'index')
		}
	}

	def create_comum() {
		Usuario usuario_login = session.user
		if(usuario_login == null){
			session.user = null
			respond new Usuario(params)
		}else{
			flash.message = ""
			redirect(controller:'usuario',action:'login')
		}
	}

	@Transactional
	def save(Usuario usuarioInstance) {
		if (usuarioInstance == null) {
			notFound()
			return
		}

		if (usuarioInstance.hasErrors()) {
			respond usuarioInstance.errors, view:'create'
			return
		}

		usuarioInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
				redirect usuarioInstance
			}
			'*' { respond usuarioInstance, [status: CREATED] }
		}
	}

	def edit(Usuario usuarioInstance) {
		respond usuarioInstance
	}

	@Transactional
	def update(Usuario usuarioInstance) {
		if (usuarioInstance == null) {
			notFound()
			return
		}

		if (usuarioInstance.hasErrors()) {
			respond usuarioInstance.errors, view:'edit'
			return
		}

		usuarioInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
				redirect usuarioInstance
			}
			'*'{ respond usuarioInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(Usuario usuarioInstance) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0 ){

				if (usuarioInstance == null) {
					notFound()
					return
				}

				usuarioInstance.delete flush:true

				request.withFormat {
					form multipartForm {
						flash.message = message(code: 'default.deleted.message', args: [message(code: 'Usuario.label', default: 'Usuario'), usuarioInstance.id])
						redirect action:"index", method:"GET"
					}
					'*'{ render status: NO_CONTENT }
				}
			}else{
				flash.message = "Desculpe, o usuario nao tem permissao."
				redirect(controller:'usuario',action:'index')
			}
		}
		else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'usuario',action:'index')
		}
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
