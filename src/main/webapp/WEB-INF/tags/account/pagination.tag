<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%@ attribute name="queryForm" type="java.lang.String" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
  ~ Copyright 2016 Cnlyml
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<%
	int current = page.getNumber() + 1;
	int begin = Math.max(1, current - paginationSize / 2);
	int end = Math.min(begin + (paginationSize - 1),
			page.getTotalPages());

	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
%>

	<ul class="pagination">
		 <%
		 	if (page.hasPrevious()) {
		 %>
               	<li><a href="javascript:goPage('1', '${sortType}', '${queryForm}');">&lt;&lt;</a></li>
                <li><a href="javascript:goPage('${current-1}', '${sortType}', '${queryForm}');">&lt;</a></li>
         <%
         	} else {
         %>
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
         <%
         	}
         %>
 
		<c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                    <li class="active"><a href="javascript:goPage('${i}', '${sortType}', '${queryForm}');">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="javascript:goPage('${i}', '${sortType}', '${queryForm}');">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
	  
	  	 <%
	  	  	 	if (page.hasNext()) {
	  	  	 %>
               	<li><a href="javascript:goPage('${current+1 }', '${sortType}', '${queryForm}');">&gt;</a></li>
                <li><a href="javascript:goPage('${page.totalPages}', '${sortType}', '${queryForm}');">&gt;&gt;</a></li>
         <%
         	} else {
         %>
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
         <%
         	}
         %>
	</ul>
	
	<script type="text/javascript">
	 function goPage(page, sortType, queryForm){
		 $("#" + queryForm).append('<input type="hidden" name="page" value="'+page+'"><input type="hidden" name="sortType" value="'+sortType+'">');
		 
		 $("#" + queryForm).submit();
	 }
	</script>
