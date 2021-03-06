<!DOCTYPE html>
<html lang="en">
<head>
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
    <form name="linkForm" method="post">
    <input type="hidden"  class="input"  name="viewType" value=""/>
                    <ul id="menu">
		  <li><a href="#" onClick="performAction('home');"><span><span>Enroll Courses</span></span></a></li>
		   <li ><a href="#" onClick="performAction('managecourses');"><span><span>My Courses</span></span></a></li>
		   <li  id="menu_active"><a href="#" onClick="performAction('viewassignments');"><span><span>View Tasks</span></span></a></li>
		   <li><a href="#" onClick="performAction('viewgrades');"><span><span>My Grades</span></span></a></li>
<!-- 		  <li ><a href="#" onClick="performAction('services');"><span><span>Service Requests</span></span></a></li>
 -->        
  <li><a href="#" onClick="performAction('viewprofile');"><span><span>Update Profile</span></span></a></li>
  <li class="end"><a href="#" onClick="logoutAction('logout');"><span><span>SignOut</span></span></a></li>
       
      </ul>
      </form>
    </nav>
    <script type="text/javascript">
    
    function logoutAction(viewType) {
    	
    	document.linkForm.viewType.value=viewType;
    	document.linkForm.action = "loginServlet";
    	document.linkForm.submit();
    }
    
    function performAction(viewType) {
    	document.linkForm.viewType.value=viewType;
    	document.linkForm.action = "servicesServlet";
    	if(viewType=='viewprofile') {
    		document.linkForm.action = "profileServlet";
    	}
    	document.linkForm.submit();
    }
        
    function deleteCourse(id) {
    	document.ContactForm.viewType.value="dropcoursedetails";
    	document.ContactForm.selectedid.value=id;
    	var r = confirm("Are you sure to drop the course ? ");
    	if(r==true) {
    		document.ContactForm.submit();
    	}else {
    		return false;
    	}
    
    }
    </script>

  </header>
  <!-- / header -->
  <!--content -->
  <section id="content">
    <div class="wrapper pad1">
	
	    </div>
    <form name="ContactForm" id="ContactForm" method="post" action="servicesServlet">
    <input type="hidden" name="viewType" value="" />
    <input type="hidden" name="selectedid" value="" />
   
<table width="100%" >
    <tr>
        <td colspan="7" align="center"> <h4>Assigned Tasks</h4> </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
     <tr>
     <th>Course Name</th>
     <th>Task</th>
     <th>Description</th>
     <th>Total Marks</th>
     <th>Due Date</th>
     <th>Professor Name</th>
      </tr> 
      
      <tr><td colspan="7">&nbsp;</td></tr> 
    <core:forEach var="assign" items="${assignmentsList}">
    <tr align="center">
    <td><core:out value="${assign.courseName}"></core:out> </td>
    <td><core:out value="${assign.title}"></core:out> </td>
    <td><core:out value="${assign.description}"></core:out> </td>
    <td><core:out value="${assign.marks}"></core:out> </td>
    <td><core:out value="${assign.deadLine}"></core:out> </td>
     <td><core:out value="${assign.professorName}"></core:out> </td>
    
    </tr>
    
    </core:forEach>
    </table>
        </form>
  </section>
  
</div>
<script type="text/javascript">Cufon.now();</script>
</body>
</html>