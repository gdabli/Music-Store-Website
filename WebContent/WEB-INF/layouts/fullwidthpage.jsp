<!DOCTYPE html>
<?xml version="1.0" encoding="UTF-8" ?>
<jsp:directive.include file="/WEB-INF/layouts/includes.jsp"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="EN" lang="EN" dir="ltr">
    <head profile="http://gmpg.org/xfn/11">
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
        <meta name="keywords" content="ITI, Examination, Training, Courses, Education, 9 Month Program, 9 Month Diploma,, 9 Month, Egypt"/>
        <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request"/>
        <link type="image/x-icon" rel="shortcut icon" href="${contextPath}/resources/images/itiLogo.png"/>
        <!--        <noscript>
                    <meta http-equiv="Refresh" content="0; URL=${contextPath}/errors/script.htm" />
                </noscript>-->

        

        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/jquery-ui.css"/>
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/alternative.css" />
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/messages.css" />

        <script src="${contextPath}/resources/js/jquery-1.11.0.js"></script>
        <script src="${contextPath}/resources/js/jquery-ui.js"></script>
        <script src="${contextPath}/resources/js/script.js"></script>


        <link rel="shortcut icon" href="images/favicon.ico" />

        <meta charset="utf-8"/>		
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/> 

        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/demo.css" />
        <!--[if lt IE 9]>
        <link rel="stylesheet" type="text/css" href="css/style_ie.css" />
        <![endif]-->

        <!-- jmpress plugin -->
        <script type="text/javascript" src="${contextPath}/resources/js/jmpress.js"></script>
        <!-- jmslideshow plugin : extends the jmpress plugin -->
        <script type="text/javascript" src="${contextPath}/resources/js/jquery.jmslideshow.js"></script>
        <script type="text/javascript" src="${contextPath}/resources/js/modernizr.custom.48780.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
        <noscript>
            <style>
                .step {
                    width: 100%;
                    position: relative;
                }
                .step:not(.active) {
                    opacity: 1;
                    filter: alpha(opacity=99);
                    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(opacity=99)";
                }
                .step:not(.active) a.jms-link{
                    opacity: 1;
                    margin-top: 40px;
                }
            </style>
        </noscript>

        <script>
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function(e) {
                        $('#blah')
                                .attr('src', e.target.result)
                                .width(150)
                                .height(150);
                    };
                    reader.readAsDataURL(input.files[0]);
                }
            }
        </script>

        <tiles:insertAttribute name="scripts" />  
    </head>
    <body>
        <div class='mainblock'>
            <tiles:insertTemplate template="header.jsp" />
            <tiles:insertAttribute name="content" />
            <tiles:insertTemplate template="footer.jsp" />
        </div>
        <style id="antiClickjack">body{display:none !important;}</style> 
        <script type="text/javascript">
            if (self == top) {
                var antiClickjack = document.getElementById("antiClickjack");
                antiClickjack.parentNode.removeChild(antiClickjack);
            }
            else {
                top.location = self.location;
            }
        </script> 
    </body>
</html>