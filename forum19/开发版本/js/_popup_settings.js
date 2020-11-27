/*
	Button switching
*/
(function(){
	var ttPopupSettings= $('#js-settings-btn');
	if (ttPopupSettings.length){
		 ptToggleCol();
	};
	function ptToggleCol() {
		var $body = $('body'),
			$html = $('html'),
			$popupSettings = $('#js-popup-settings'),
			$btnClose = $('.tt-btn-col-close');

        $('#js-settings-btn').on('click', function (e){
            e.preventDefault();
            if($(this).hasClass('column-open')){
                $btnClose.trigger('click');
                return false;
            };
            $(this).addClass('column-open');
            var ptScrollValue = $body.scrollTop() || $html.scrollTop();
            $popupSettings.toggleClass('column-open').perfectScrollbar();
            $body.css("top", - ptScrollValue).addClass("no-scroll").append('<div class="modal-filter"></div>');
            var modalFilter = $('.modal-filter').fadeTo('fast',1);
            if (modalFilter.length) {
                modalFilter.on('click', function(){
                    $btnClose.trigger('click');
                })
            };
            return false;
        });
        $btnClose.on('click', function(e){
            e.preventDefault();
            ttPopupSettings.removeClass('column-open');
            $popupSettings.removeClass('column-open').perfectScrollbar('destroy');
            var top = parseInt($body.css("top").replace("px", ""), 10) * -1;
            $body.removeAttr("style").removeClass("no-scroll").scrollTop(top);
            $html.removeAttr("style").scrollTop(top);
            $(".modal-filter").off().remove();
            return false;
        });
    };
})();