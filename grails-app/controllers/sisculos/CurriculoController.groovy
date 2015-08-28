package sisculos
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CurriculoController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def initialCurriculo() {
		render(template:'form_cep')
	}

	def consultaCEP = {
		//obtendo o valor digitado no formulario e retirando o '-' para pesquisa
		def cep = params.cep.toString().replace("-","")
		Endereco cepInstance = new Endereco(cep);
		//jogando os dados no array de string
		String[] getdados = cepInstance.consultaCEP();
		//criando a litsta do tipo CEP para ser usada no retorno dos dados no ajax
		def list = Endereco.list();
		//verificando se o campo bairro veio preenchido com a informaçãpo 'soap:Server', isso quer dizer que não houve retorno
		if(!getdados[0].toString().equalsIgnoreCase("soap:Server")){
			//adicionando os dados ao objeto CEP para ser enviado ao formulario
			cepInstance.bairro = getdados[0];
			cepInstance.cep  = getdados[1];
			cepInstance.cidade  = getdados[2];
			cepInstance.endereco = getdados[5];
			cepInstance.complemento = getdados[4];
			cepInstance.uf = getdados[7];
			list.add(cepInstance)
			//renderizando o form com as informações obtidas no webservice
			render(template:'form_cep', model:[consultacep:list])
		}else{
			// se não houve retorno nullamos a list e rederizamos os campos vazios
			list.add(null)
			render(template:'form_cep', model:[consultacep:list])
		}
	}

	def index(Integer max) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0 || usuario_login.permissao == 2){
				params.max = Math.min(max ?: 10, 100)
				respond Curriculo.list(params), model:[curriculoInstanceCount: Curriculo.count()]
			}else if(usuario_login.permissao == 1 ){
				//tras somente o curriculo do usuário logado
				Curriculo teste = usuario_login ? Curriculo.findByUsuario(usuario_login) : []
				if(teste != null){
					respond Curriculo.findAllWhere(usuario: teste.usuario), model:[curriculoInstanceCount: Curriculo.count()]
				}
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'usuario',action:'login')
		}
	}

	def show(Curriculo curriculoInstance) {
		respond curriculoInstance
	}

	def create() {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if( usuario_login.permissao == 1){
				//testar se existe curriculo cadastrado para o usuario
				Curriculo teste_curriculo_usuario = usuario_login ? Curriculo.findByUsuario(usuario_login) : []
				if(teste_curriculo_usuario != null){ // se já houver curriculo, não permite cadastrar
					flash.message = "Desculpe, o usuario ja possui um curriculo cadastrado."
					redirect(controller:'curriculo',action:'index')
				}else{
					respond new Curriculo(params)
				}
			}else if(usuario_login.permissao == 0){
				respond new Curriculo(params)
			}else{
				flash.message = "Desculpe, o usuario nao tem permissao."
				redirect(controller:'curriculo',action:'index')
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'curriculo',action:'index')
		}
	}

	@Transactional
	def save(Curriculo curriculoInstance) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0 || usuario_login.permissao == 1){
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
			}else{
				flash.message = "Desculpe, o usuario nao tem permissao."
				redirect(controller:'curriculo',action:'index')
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'curriculo',action:'index')

		}
	}

	def edit(Curriculo curriculoInstance) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0 || usuario_login.permissao == 1){
				respond curriculoInstance
			}else{
				flash.message = "Desculpe, o usuario nao tem permissao."
				redirect(controller:'curriculo',action:'index')
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'curriculo',action:'index')

		}
	}

	@Transactional
	def update(Curriculo curriculoInstance) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0 || usuario_login.permissao == 1){
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
			}else{
				flash.message = "Desculpe, o usuario nao tem permissao."
				redirect(controller:'curriculo',action:'index')
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'curriculo',action:'index')

		}
	}

	@Transactional
	def delete(Curriculo curriculoInstance) {
		Usuario usuario_login = session.user
		if(usuario_login != null){
			if(usuario_login.permissao == 0 ){
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
			}else{
				flash.message = "Desculpe, o usuario nao tem permissao."
				redirect(controller:'curriculo',action:'index')
			}
		}else{
			flash.message = "Desculpe, precisa estar autenticado no sistema."
			redirect(controller:'curriculo',action:'index')

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
