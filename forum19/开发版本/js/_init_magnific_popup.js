/*
	Init magnific popup
*/
(function(){
	$('#tt-pageContent .tt-gallery-layout').each(function(){
        $(this).magnificPopup({
        	delegate: 'a',
			type: 'image',
			gallery:{
			    enabled:true
			},
			mainClass: 'mfp-with-zoom',
		});
    });
})();
