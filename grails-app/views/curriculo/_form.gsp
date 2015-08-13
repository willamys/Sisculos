<%@ page import="sisculos.Curriculo" %>



<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="curriculo.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${curriculoInstance?.nome}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'cpf', 'error')} required">
	<label for="cpf">
		<g:message code="curriculo.cpf.label" default="Cpf" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cpf" maxlength="14" required="" value="${curriculoInstance?.cpf}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'rg', 'error')} required">
	<label for="rg">
		<g:message code="curriculo.rg.label" default="Rg" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="rg" required="" value="${curriculoInstance?.rg}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="curriculo.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${curriculoInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'telefone', 'error')} required">
	<label for="telefone">
		<g:message code="curriculo.telefone.label" default="Telefone" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="telefone" maxlength="14" required="" value="${curriculoInstance?.telefone}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'formacao', 'error')} required">
	<label for="formacao">
		<g:message code="curriculo.formacao.label" default="Formacao" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="formacao" required="" value="${curriculoInstance?.formacao}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'experiencia', 'error')} required">
	<label for="experiencia">
		<g:message code="curriculo.experiencia.label" default="Experiencia" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="experiencia" required="" value="${curriculoInstance?.experiencia}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'datanasc', 'error')} required">
	<label for="datanasc">
		<g:message code="curriculo.datanasc.label" default="Datanasc" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="datanasc" maxlength="10" required="" value="${curriculoInstance?.datanasc}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'sexo', 'error')} required">
	<label for="sexo">
		<g:message code="curriculo.sexo.label" default="Sexo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="sexo" from="${curriculoInstance.constraints.sexo.inList}" required="" value="${curriculoInstance?.sexo}" valueMessagePrefix="curriculo.sexo"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'endereco', 'error')} required">
	<label for="endereco">
		<g:message code="curriculo.endereco.label" default="Endereco" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="endereco" name="endereco.id" from="${sisculos.Endereco.list()}" optionKey="id" required="" value="${curriculoInstance?.endereco?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: curriculoInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="curriculo.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${sisculos.Usuario.list()}" optionKey="id" required="" value="${curriculoInstance?.usuario?.id}" class="many-to-one"/>

</div>

