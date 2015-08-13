import sisculos.Usuario

class BootStrap {

    def init = { servletContext ->
		
		def userdefault = new Usuario(login:'administrador', senha:'password', permissao:'0')
		userdefault.save()
    }
    def destroy = {
    }
}
