(function($){
 function initnewsLetterObj(valueData, $obj){
         setTimeout(function() {
           $obj.modal('show');
         }, valueData);
    };
    $('.modal').each(function(){
        var $obj = $(this);
        if($obj.attr("data-pause")){
            var valueData = $(this).attr('data-pause');
            initnewsLetterObj(valueData, $obj);
        }
    });
})(jQuery);
