
var lyrOpen = function (o){
	"use strict";
	var a = -$(window).scrollTop();
	$('body').css('top',a).addClass('nav-open'); 
}

var lyrClose = function (o){
	'use strict';
	var originScroll = -$('body').position().top;
	$('body').removeClass(o+'-open');
	setTimeout(function(){ 
		$('body').removeClass('nav-open'); 
		if(originScroll!=-0){
			$(window).scrollTop(originScroll);
		}
		$('body').removeAttr('style');
	},200); 
}


$(function (){   
	'use strict';
	var $window = $(window),
		$body=$('body');
    
	$('[data-toggle=menu]').click(function(){
        var o = $(this).attr('href'),
            o = o.split('#')[1];
		$body.hasClass('nav-open') ? lyrClose(o) : lyrOpen(o);
		return false;
	});
	$('[data-toggle=close]').click(function(){
        var o = $(this).data('target'),
            o = o.split('#')[1];
        lyrClose(o);
		return false;
	});

	$('.full-nav .dep1 li').click(function(e){
		e.preventDefault(); e.stopPropagation();
		var parentClass = "active"+$(this).data('active') ;
		$('#gnb-full').attr('class',parentClass);
		$('.full-nav .dep1 li').removeClass('active');
		$(this).addClass('active');
	});

	$(".dep2-list").mCustomScrollbar({
		theme:"dark-thick"
	});


	$window.scroll(function(){
		if($window.scrollTop() > (360 - 90) ){
			$('#hd').addClass('scroll') ;
		} else {
			$('#hd').removeClass('scroll') ;
		}
	});
});