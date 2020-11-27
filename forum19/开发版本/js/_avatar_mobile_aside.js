/*
    Init Avatar Mobile Aside
*/
(function(){
	var $ttToggleAside = $('#tt-pageContent .tt-toggle-aside'),
		$body = $('body'),
		$html = $('html'),
		$jsAside = $('#js-aside');

	$body.on('click', '#tt-pageContent .tt-toggle-aside', function(e){
		var ttScrollValue = $body.scrollTop() || $html.scrollTop();
        $jsAside.toggleClass('column-open');
        $body.css("top", - ttScrollValue).addClass("no-scroll").append('<div class="modal-filter"></div>');
        var modalFilter = $('.modal-filter').fadeTo('fast',1);
        if (modalFilter.length) {
            modalFilter.on('click', function(){
                $btnClose.trigger('click');
            })
        }
		return false;
	});
	$body.on('click', '.tt-list-avatar .tt-item',  function(e){
		$jsAside.removeClass('column-open');
		var top = parseInt($body.css("top").replace("px", ""), 10) * -1;
		$body.removeAttr("style").removeClass("no-scroll").scrollTop(top);
		$html.removeAttr("style").scrollTop(top);
		$(".modal-filter").off().remove();
		return false;
	});
	$(window).resize(function(e){
      $('.tt-list-avatar .tt-item').trigger('click');
    });
})();
