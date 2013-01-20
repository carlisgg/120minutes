$(document).ready(function() {

	// Function to activate the tab
	function activateTab() {
		var activeTab = $('[href=' + window.location.hash.replace('/', '') + ']');
		activeTab && activeTab.tab('show');
	}
	
	// Trigger when the hash changes (forward / back)
	$(window).bind('hashchange', function(){
		activateTab();
	});
	
	activateTab();
	
	// Change hash when a tab changes
    $('a[data-toggle="tab"], a[data-toggle="pill"]').on('shown', function(){
        window.location.hash = $(this).attr('href').replace('#', '');
    });
    
    $("#add-topic-link").click(function() {
    	$("#add-topic-form").show("slow");
    });
    
    var profileInfoEdit=false;
    $("#profile-info-button").click(function() {
    	if(!profileInfoEdit){
	    	$("#profile-info-view").fadeOut('slow', function() {
	    		$("#profile-info-edit").fadeIn('slow');
	    		$("#profile-info-button i").removeClass("icon-pencil").addClass("icon-arrow-left");
	    		profileInfoEdit=true;
	        });
    	}else{
    		$("#profile-info-edit").fadeOut('slow', function() {
	    		$("#profile-info-view").fadeIn('slow');
	    		$("#profile-info-button i").removeClass("icon-arrow-left").addClass("icon-pencil");
	    		profileInfoEdit=false;
	        });
    	}
    });
    
});