<!DOCTYPE html>
<html lang="en">
<head>
<title>Student Reporting System</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<script type="text/javascript" src="js/jquery-1.5.2.js" ></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Cabin_400.font.js"></script>
<script type="text/javascript" src="js/tabs.js"></script>
<script type="text/javascript" src="js/jquery.jqtransform.js" ></script>
<script type="text/javascript" src="js/jquery.nivo.slider.pack.js"></script>
<script type="text/javascript" src="js/atooltip.jquery.js"></script>
<script type="text/javascript" src="js/script.js"></script>

<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/datetime-1.0" prefix="dt" %>

</head>
<body id="page4">
<div class="main">
    <header>
      <div class="wrapper">
      <h1>  </h1>
       <h1>&nbsp  </h1>
	   <h1>&nbsp  </h1>
	   <br/>
	   
	   <br/>
	   <h1>&nbsp  </h1>
    </div>

	<nav>
            <ul id="menu">
      <core:if test="${user.userType=='admin'}">
        <li><a href="#"><span><span>Manage Courses</span></span></a></li>
         <li  id="menu_active"><a href="#" onClick="modifyFunction('viewstudents');"><span><span>Registered Students</span></span></a></li>
        </core:if>
        <core:if test="${user.userType=='staff'}">
        <li ><a href="adddepartments.jsp"><span><span>Assign Task</span></span></a></li>
        <li id="menu_active"><a href="#" onClick="modifyFunction('postgrades');"><span><span>Grade Task</span></span></a></li>
        </core:if>
        <li><a href="#" onClick="modifyFunction('enquiryhome');"><span><span>Track Tickets</span></span></a></li>
        <li class="end"><a href="login.jsp"><span><span>SignOut</span></span></a></li>
      </ul>
    </nav>
    <script type="text/javascript">

    function modifyFunction(viewType) {

    	document.ContactForm.viewType.value=viewType;
    	if(viewType=='enquiryhome') {
    		document.ContactForm.action="enquiryServlet";
    	}
    	document.ContactForm.submit();
    }

    
	function updateEnquiry(enquiryId,updateId) {
    	document.ContactForm.viewType.value="viewstudents";
    	document.ContactForm.selectedstudent.value=enquiryId;
    	document.ContactForm.selectedupdate.value=updateId;
    	document.ContactForm.submit();
    }
    
    </script>
    
  </header>
  <!-- / header -->
  <!--content -->
  <section id="content">
    <div class="wrapper pad1">
	
	      <article class="col1">
     
      </article>  
	
      <article class="col2">
      
        <form name="ContactForm" id="ContactForm" action="viewDepartmentsServlet" method="post">
        <input type="hidden"  class="input"  name="viewType" required="required" value="viewstudentdetails"/>
        <input type="hidden"  class="input"  name="selectedstudent" required="required" value=""/>
        <input type="hidden"  class="input"  name="selectedupdate" required="required" value=""/>
        
    </form>
    
      </article>
    </div>
      <core:if test="${user.userType=='staff'}">
    <table width="100%" >
    <tr>
        <td colspan="7"> <h4>Task Details</h4> </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
     <tr>
     <th>Course Name</th>
     <th>Task Title</th>
     <th>Description</th>
     <th>Points</th>
     <th>Due Date</th>
     <th>Action</th>
      </tr> 
      
      <tr><td colspan="7">&nbsp;</td></tr> 
    <core:forEach var="assign" items="${assignmentsList}">
    <tr align="center">
    <td><core:out value="${assign.courseName}"></core:out> </td>
    <td><core:out value="${assign.title}"></core:out> </td>
    <td><core:out value="${assign.description}"></core:out> </td>
    <td><core:out value="${assign.marks}"></core:out> </td>
    <td><core:out value="${assign.deadLine}"></core:out> </td>
     
    <td><a href="#"  onClick="updateEnquiry('<core:out value="${assign.courseId}"/>','<core:out value="${assign.updateId}"/>');">Grade Task</a></td>
    
    </tr>
    
    </core:forEach>
    </table>
    </core:if>
    
  </section>
  <!--content end-->
  <!--footer -->
  <footer>
    <div class="wrapper">
      
    </div>
  </footer>
  <!--footer end-->
</div>
<script type="text/javascript">Cufon.now();</script>
</body>
</html>