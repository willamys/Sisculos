if (typeof jQuery !== 'undefined') {
	$(document).ready(function(){
		$("#telefone").mask("(99)99999-9999");
		$("#cpf").mask("999.999.999-99");
		$("#cep").mask("99999-999");
		$("#data").mask("99/99/9999");
	});
}