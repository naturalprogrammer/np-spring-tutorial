<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="includes/header.jsp"%> 

<div class="panel panel-primary">
  <div class="panel-heading">Please sign up</div>
  <div class="panel-body">
	<form:form modelAttribute="user" role="form">
	
	  <form:errors cssClass="error" />
	  
	  <div class="form-group">
		<form:label path="email">Email address</form:label>
		<form:input path="email" type="email" class="form-control"
	            placeholder="Enter email" />
		<form:errors path="email" cssClass="error" />
	  </div>
	  <div class="form-group">
		<form:label path="name">Name</form:label>
		<form:input path="name" class="form-control" placeholder="Enter name" />
		<form:errors path="name" cssClass="error" />
	  </div>
	  <div class="form-group">
		<form:label path="password">Password</form:label>
		<form:password path="password" class="form-control" placeholder="Password" />
		<form:errors path="password" cssClass="error" />
	  </div>
	  <button type="submit" class="btn btn-primary">Submit</button>
	</form:form>
  </div>
</div>

<%@include file="includes/footer.jsp"%>