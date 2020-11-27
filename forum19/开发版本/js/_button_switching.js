/*
	Button switching
*/
(function(){
	var ttJsActiveBtn= $('#tt-pageContent .tt-js-active-btn');
	if (ttJsActiveBtn.length){
		ttJsActiveBtn.on('click', '.tt-button-icon', function(e){
			$(this).closest(ttJsActiveBtn).find('.tt-button-icon').removeClass('active');
			$(this).addClass('active');
			return false;
		});
	};
})();