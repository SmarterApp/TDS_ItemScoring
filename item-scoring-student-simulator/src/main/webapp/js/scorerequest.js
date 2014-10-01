function request() {
	var postUrl = $('#j_id_a\\:request-url').val();
	var postData = $('#request-body').val();
	$.post(postUrl, postData, function(respData, status, jqXHR) {
		$('textarea#response-body').val(jqXHR.responseText);
		//alert(jqXHR.responseText);
	});
}

function fetchResponse(contextToken) {
	
}