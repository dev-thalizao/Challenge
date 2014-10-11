<%@ page import="challenge.Challenge" %>



<div class="fieldcontain ${hasErrors(bean: challengeInstance, field: 'details', 'error')} required">
	<label for="details">
		<g:message code="challenge.details.label" default="Details" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="details" required="" value="${challengeInstance?.details}"/>

</div>

