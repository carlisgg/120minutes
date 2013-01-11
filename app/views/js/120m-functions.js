$(document).ready(function() {
	
	$(".btn-group.btn-donate").hover(
	
		function() {
			var theButton = $(this).find('.btn');
			var theIcon = $(this).find('i');
			$(theButton).addClass("btn-success");
			$(theIcon).removeClass("icon-home");
			$(theIcon).addClass("icon-thumbs-up");
			$(theIcon).addClass("icon-white");
		},
		function() {
			var theButton = $(this).find('.btn-success');
			var theIcon = $(this).find('i');
			$(theButton).removeClass("btn-success");
			$(theIcon).removeClass("icon-thumbs-up");
			$(theIcon).removeClass("icon-white");
			$(theIcon).addClass("icon-home");
		}
	
	);
});