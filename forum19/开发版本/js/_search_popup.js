/*
    Search popup
*/
(function(){
    var ttSearchWrapper = $('#tt-header .tt-search'),
        ttSearchToggle = ttSearchWrapper.find('.tt-search-toggle'),
        ttSearchResults = ttSearchWrapper.find('.search-results'),
        ttSearchInput = ttSearchWrapper.find('.tt-search__input');

    if (ttSearchInput.length && ttSearchResults.length){
         ttSearchInput.on("input",function(ev){
            if($(ev.target).val()){
                ttSearchResults.fadeIn("200");
                ttSearchResults.find('.tt-search-scroll').perfectScrollbar();
            };
        });
        ttSearchInput.blur(function(){
          ttSearchResults.fadeOut("200");
        });
    };
})();
