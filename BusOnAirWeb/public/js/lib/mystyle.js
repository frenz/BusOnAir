
$(function	()	{

	//Collapsible Sidebar Menu
	$('.openable > a').click(function()	{
		
		if(!$('#wrapper').hasClass('sidebar-mini'))	
		{}
		else{
			$('#wrapper').removeClass('sidebar-mini');
		}
		if( $(this).parent().children('.submenu').is(':hidden') ) {
			$(this).parent().siblings().removeClass('open').children('.submenu').slideUp();
			$(this).parent().addClass('open').children('.submenu').slideDown();
		}
		else	{
			$(this).parent().removeClass('open').children('.submenu').slideUp();
		}
		return false;
	});
		
	//Toggle Menu
	$('#sidebarToggle').click(function()	{
		$('#wrapper').toggleClass('sidebar-display');
		$('.main-menu').find('.openable').removeClass('open');
		$('.main-menu').find('.submenu').removeAttr('style');
	});
	
	$('#sizeToggle').click(function()	{
	
		$('#wrapper').off("resize");
	
		$('#wrapper').toggleClass('sidebar-mini');
		$('.main-menu').find('.openable').removeClass('open');
		$('.main-menu').find('.submenu').removeAttr('style');
	});
	
	if(!$('#wrapper').hasClass('sidebar-mini'))	{ 
		if (Modernizr.mq('(min-width: 768px)') && Modernizr.mq('(max-width: 868px)')) {
			$('#wrapper').addClass('sidebar-mini');
		}
		else if (Modernizr.mq('(min-width: 869px)'))	{
			if(!$('#wrapper').hasClass('sidebar-mini'))	{
			}
		}
	}
	
	$(window).resize(function() {
		if (Modernizr.mq('(min-width: 768px)') && Modernizr.mq('(max-width: 868px)')) {
			$('#wrapper').addClass('sidebar-mini').addClass('window-resize');
			$('.main-menu').find('.openable').removeClass('open');
			$('.main-menu').find('.submenu').removeAttr('style');
		}
		else if (Modernizr.mq('(min-width: 869px)'))	{
			if($('#wrapper').hasClass('window-resize'))	{
				$('#wrapper').removeClass('sidebar-mini window-resize');
				$('.main-menu').find('.openable').removeClass('open');
				$('.main-menu').find('.submenu').removeAttr('style');
			}
		}
		else	{
			$('#wrapper').removeClass('sidebar-mini window-resize');
			$('.main-menu').find('.openable').removeClass('open');
			$('.main-menu').find('.submenu').removeAttr('style');
		}
	});
	
	//fixed Sidebar
	$('#fixedSidebar').click(function()	{
		if($(this).prop('checked'))	{
			$('aside').addClass('fixed');
		}	
		else	{
			$('aside').removeClass('fixed');
		}
	});


	$("#main-container").css("height",$(window).height() -45+"px");



});


