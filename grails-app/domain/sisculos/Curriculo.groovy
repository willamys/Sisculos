package sisculos

class Curriculo {

    String nome
	String cpf
	String rg
	String sexo
	String email
	String telefone
	String formacao
	String experiencia
	String datanasc
	Endereco endereco
	Usuario usuario

	static constraints = {
		nome(blank: false)
		cpf(blank: false,size: 14..14)
		rg(blank: false)
		sexo inList:["M", "F"]
		email(email: true, blank: false)
		telefone(blank: false,size: 14..14)
		formacao(blank: false)
		experiencia(blank: false)
		datanasc(blank: false,size: 10..10)
	}
}
