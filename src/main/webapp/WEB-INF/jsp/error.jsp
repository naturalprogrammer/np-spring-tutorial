<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="includes/header.jsp"%>
	
	<div class="alert alert-danger">
		<p>
			There was an unexpected error
		      (type=${error}, status=${status}): <strong>${message}</strong>
		</p>
		<p>Click <a href="/">here</a> to go to home page</a></p>
	</div>

<%@include file="includes/footer.jsp"%>
