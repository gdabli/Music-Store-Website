<script type="text/javascript">
    $(function() {
    $('#da-slider').cslider({
    autoplay: true,
            interval:10000,
            bgincrement: 0
    });
    });
            var max = 0;
            $(window).resize(function() {
    setPageWidth();
    });
            function loadStaticMenu(){
            var token = $('input[name="CSRFToken"]').val();
                    $.ajax({
                    url: contexPath + '/getStaticMenus',
                            type: 'POST',
                            async: false,
                            data: "CSRFToken=" + token,
                            success: function(response) {
                            var menuItemsFlag = true;
                                    var menusValue = "<li><a href='" + contexPath + "/home.htm'>Home</a></li>";
                                    menusValue += "<li><a href='" + contexPath + "/newsView.htm'>News</a></li>";
                                    menusValue += "<li><a href='" + contexPath + "/activitiesView.htm'>Activities</a></li>";
                                    for (i = 0; i < response.length; i++){
                            var menu = response[i];
                                    var name = menu.name;
                                    var menuValue = "<li><a href='javascript:void(0);'>";
                                    menuValue += name + "</a><ul>";
                                    var menuItems = menu.menuItems;
                                    menuItemsFlag = true;
                                    for (h = 0; h < menuItems.length; h++){
                            menuItemsFlag = false;
                                    var menuItem = menuItems[h];
                                    var menuItemName = menuItem.name;
                                    var menuItemId = menuItem.id;
                                    menuValue += "<li><a href='" + contexPath + "/pageView/" + menuItemId + "'>";
                                    menuValue += menuItemName + "</a></li>";
                            }
                            menuValue += "</ul></li>";
                                    if (menuItemsFlag)
                                    menuValue = "";
                                    menusValue += menuValue;
                            }
                            //                alert(menusValue);
                            $('#smoothmenu11').html(menusValue);
                            },
                            error: function(e){
                            //                alert('Error: ' + e.responseText);  
                            }
                    });
            }
    function loadDynamicMenu(){
    var token = $('input[name="CSRFToken"]').val();
            $.ajax({
            url: contexPath + '/getDynamicMenus',
                    type: 'POST',
                    async: false,
                    data: "CSRFToken=" + token,
                    success: function(response) {
                    var menuItemsFlag = true;
                            var menusValue = "<li><a href='javascript:void(0);'>Training Programs</a>";
                            menusValue += "<ul class='side_sub_menu'>";
                            menusValue += "<li><a href='" + contexPath + "/coursesView.htm'>Courses&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>";
                            menusValue += "<li><a href='" + contexPath + "/tracksView.htm'>Tracks</a></li>";
                            menusValue += "<li><a href='" + contexPath + "/programsView.htm'>Learning Paths</a></li>";
                            menusValue += "</ul></li>";
                            for (i = 0; i < response.length; i++){
                    var menu = response[i];
                            var name = menu.name;
                            var menuValue = "<li><a href='javascript:void(0);'>";
                            menuValue += name + "</a><ul class='side_sub_menu'>";
                            var menuItems = menu.menuItems;
                            menuItemsFlag = true;
                            for (h = 0; h < menuItems.length; h++){
                    menuItemsFlag = false;
                            var menuItem = menuItems[h];
                            var menuItemName = menuItem.name;
                            var menuItemId = menuItem.id;
                            menuValue += "<li><a href='" + contexPath + "/pageView/" + menuItemId + "'>";
                            menuValue += menuItemName + "</a></li>";
                    }
                    menuValue += "</ul></li>";
                            if (menuItemsFlag)
                            menuValue = "";
                            menusValue += menuValue;
                    }
                    menusValue += "<li><a href='" + contexPath + "/joinMailingList.htm'>Join Mailing List</a></li>";
                            $('#smoothmenu3').html(menusValue);
                    },
                    error: function(e){
                    //                alert('Error: ' + e.responseText);  
                    }
            });
    }
    function setupPage(){
    //        alert(someVar);
    setPageWidth();
            loadStaticMenu();
            loadDynamicMenu();
            ddsmoothmenu.init({
            mainmenuid: "smoothmenu1", //menu DIV id
                    orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
                    classname: 'ddsmoothmenu', //class added to menu's outer DIV
                    //customtheme: ["#1c5a80", "#18374a"],
                    contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
            })
            ddsmoothmenu.init({
            mainmenuid: "smoothmenu2", //Menu DIV id
                    orientation: 'v', //Horizontal or vertical menu: Set to "h" or "v"
                    classname: 'ddsmoothmenu-v', //class added to menu's outer DIV
                    //        method: 'toggle', // set to 'hover' (default) or 'toggle'
                    //customtheme: ["#804000", "#482400"],
                    contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
            })
            //        initializeMap();
    }
    function setPageWidth(){
    var zoomRatio = 90;
            var browser = '';
            var browserVersion = 0;
            //        alert("vfsdfas");
            if (/Opera[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    zoomRatio = getZoom();
            browser = 'Opera';
    } else if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
    zoomRatio = getIEZoom();
            browser = 'MSIE';
    } else if (/Navigator[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    zoomRatio = getZoom();
            browser = 'Netscape';
    } else if (/Chrome[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    zoomRatio = getZoom();
            browser = 'Chrome';
    } else if (/Safari[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    zoomRatio = getZoom();
            browser = 'Safari';
            /Version[\/\s](\d+\.\d+)/.test(navigator.userAgent);
            browserVersion = new Number(RegExp.$1);
    } else if (/Firefox[\/\s](\d+\.\d+)/.test(navigator.userAgent)) {
    zoomRatio = getZoom();
            browser = 'Firefox';
    }
    if (browserVersion === 0){
    browserVersion = parseFloat(new Number(RegExp.$1));
    }
    //        alert(browser + "*" + browserVersion);                
    //        alert(zoomRatio);
    if (zoomRatio >= 100) {
    $('social_media_search').css('margin-left', '0%');
            if ($(this).width() < 1024)
            $('body').css('width', '1024px');
            else
            $('body').css('width', '100%');
    } else{
    $('body').css('width', '1024px');
            $('social_media_search').css('margin-left', '-2%');
    }

    $("ul.level-2").children().each(function() {
    if ($(this).height() > max)
            max = $(this).height();
    });
            $("ul.level-2").children().each(function() {
    $(this).children().css("height", max - 20 + "px");
    });
    }
    function getZoom() {
    //        alert(window.outerWidth);
    //        alert(window.innerWidth);
    return (window.outerWidth / window.innerWidth) * 100;
    }
    function getIEZoom() {
    var screen = document.frames.screen;
            //        alert(((screen.deviceXDPI / screen.systemXDPI) * 100 + 0.9).toFixed()-1);
            return ((screen.deviceXDPI / screen.systemXDPI) * 100 + 0.9).toFixed() - 1;
    }

</script>
<script type="text/javascript">
    var contexPath = "<%=request.getContextPath()%>";
            String.prototype.isValid = function() {
            if (this.indexOf("%") != - 1 || this.indexOf("&") != - 1){
            return false;
            }
            else{
            return true;
            }
            };
            String.prototype.trim = function() {
            return this.replace(/^\s+|\s+$/g, "");
            };
            String.prototype.isDecimal = function() {
            var regex = /^\d+$/;
                    return regex.test(this);
            };
            String.prototype.isEnglishValid = function() {
            var regex = /^[a-zA-Z:=/_,@!$#^*()<>\+\]\[\{\}\"\'\?\\\.\-\d\s\n\f\r\t\v\xxx\xdd\uxxxx]+$/;
                    return !regex.test(this);
            };
            String.prototype.isURLValid = function() {
            var regex = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;
                    return !regex.test(this);
            };
            String.prototype.isArabicValid = function() {
            var regex = /^[\u0600-\u06ff\ufb50-\ufdff\ufe70-\ufeff:=/_,@!$#^*()<>\+\]\[\{\}\"\'\?\\\.\-\d\s\n\f\r\t\v\xxx\xdd\uxxxx]+$/;
                    return regex.test(this);
            };
            idleTime = 0;
            var durationInMinute = 30;
            $(document).ready(function () {
    //Increment the idle time counter every minute.
    var idleInterval = setInterval(timerIncrement, 61000); // 1 minute

            //Zero the idle timer on mouse movement.
            $(this).mousemove(function (e) {
    idleTime = 0;
    });
            $(this).keypress(function (e) {
    idleTime = 0;
    });
    });
            function timerIncrement() {
            idleTime = idleTime + 1;
                    if (idleTime >= durationInMinute) {
            window.location.reload();
            }
            }
    jQuery.i18n.properties({
    name: 'messages',
            path:contexPath + '/resources/messages/',
            mode:'both',
            language:'en',
            callback: function(){
            }
    });</script>
<script>
            $(document).ready(function() {

    var startingLocation;
            //        var destination = "reading+room+manchester"; // replace this with any destination
            var destination = "30.071053, 31.021654"; // replace this with any destination

            $('a.get-directions').click(function (e) {
    e.preventDefault();
            // check if browser supports geolocation
            if (navigator.geolocation) {

    // get user's current position
    navigator.geolocation.getCurrentPosition(function (position) {

    // get latitude and longitude
    var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            startingLocation = latitude + "," + longitude;
            // send starting location and destination to goToGoogleMaps function
            goToGoogleMaps(startingLocation, destination);
    });
    }

    // fallback for browsers without geolocation
    else {

    // get manually entered postcode
    startingLocation = $('.manual-location').val();
            // if user has entered a starting location, send starting location and destination to goToGoogleMaps function
            if (startingLocation != '') {
    goToGoogleMaps(startingLocation, destination);
    }
    // else fade in the manual postcode field
    else {
    $('.no-geolocation').fadeIn();
    }

    }

    // go to Google Maps function - takes a starting location and destination and sends the query to Google Maps
    function goToGoogleMaps(startingLocation, destination) {
    window.open("https://maps.google.co.uk/maps?saddr=" + startingLocation + "&daddr=" + destination, '_new');
    }

    });
    });
</script>
