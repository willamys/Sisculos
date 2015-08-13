package sisculos

class Endereco {

	
	String cep
	String endereco
	String complemento
	String bairro
	String cidade
	String uf
	
    static constraints = {
		cep(blank: false, size: 9..9)
		endereco(blank: false)
		complemento(blank: false)
		bairro(blank: false)
		cidade(blank: false)
		uf(blank: false,size:2..2)
    }
}
