<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>合作平台</title>
<style type="text/css">
.btn {
	-webkit-border-radius:8px;  
	-moz-border-radius:8px;  
	border-radius:8px; 
	-webkit-transition:-webkit-box-shadow .2s ease-in-out;  
	-moz-transition:-moz-box-shadow .2s ease-in-out;  
	-o-transition: -o-box-shadow .2s ease-in-out;  
	transition: box-shadow .2s ease-in-out;
}
.btn-fillet {
	font-size:14px; 
	color:#0066ff;
	background-color:#ffffff;
	border:1px solid;
	border-color:#e7e7e7 #909090 #909090 #e7e7e7;
	padding:5px 10px 5px 10px;
	text-align:center;
	display:inline;
	cursor:pointer;
}
.btn-chosen { 
	font-size:14px; 
	color:#0066ff; 
	border:1px solid;
	border-color:#909090 #e7e7e7 #e7e7e7 #909090;
	padding:5px 10px 5px 10px;
	cursor:text;
}
.inputPage {
	border:1px solid;
	border-color:#808080 #e7e7e7 #e7e7e7 #808080;
	margin:0px 0px 0px 25px;
	padding:2px 5px 2px 5px; 
	height:20px;
}
</style>
</head>

<body>

    <c:set var="mouseover" value="onmouseover='overBtn(this)'"/>
	<c:set var="mouseout" value="onmouseout='outBtn(this)'"/>
<%--------------------------------------------------------------------------------------------------%>
 	<c:set var="PC" value="10"/><%----这里设置最多的数字按钮数量，不包含上页下页.(必须是偶数)----%>
<%--------------------------------------------------------------------------------------------------%>
	<c:set var="begin" value="1"/>
	<c:set var="end" value="${page.totalPages>0?page.totalPages:1}"/>
	<c:if test="${page.pageNo>1}"><div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${page.pageNo-1});" ${mouseover} ${ mouseout} > &lt;上一页</div> </c:if>
	<c:choose>
		<c:when test="${page.totalPages>PC}"> 
			<c:choose>
				<c:when test="${page.pageNo<=PC/2}">
					<c:forEach var="p" begin="${begin}" end="${PC-1}">
					<c:if test="${p==page.pageNo}"><div id="pageBtn" class="btn btn-fillet btn-chosen">${p}</div></c:if>
					<c:if test="${p!=page.pageNo}"><div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${p});" ${mouseover} ${ mouseout}>${p}</div></c:if>
					</c:forEach>
					&nbsp;……&nbsp;
					<div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${page.totalPages});" ${mouseover} ${ mouseout}>${page.totalPages}</div>
				</c:when>
				<c:when test="${page.pageNo>page.totalPages-PC/2}">
					<div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(1);" ${mouseover} ${ mouseout}>1</div>
					&nbsp;……&nbsp;
					<c:forEach var="p" begin="${end+1-PC}" end="${end}">
					<c:if test="${p==page.pageNo}"><div id="pageBtn" class="btn btn-fillet btn-chosen" >${p}</div></c:if>
					<c:if test="${p!=page.pageNo}"><div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${p});" ${mouseover} ${ mouseout}>${p}</div></c:if>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:set var="begin" value="${page.pageNo+1-PC/2}"/>
					<c:set var="end" value="${page.pageNo-1+PC/2}"/>
					<div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(1);" ${mouseover} ${ mouseout}>1</div>
					<c:if test="${page.pageNo>PC/2+1}">&nbsp;……&nbsp;</c:if>
					<c:forEach var="p" begin="${begin}" end="${end}">
					<c:if test="${p==page.pageNo}"><div id="pageBtn" class="btn btn-fillet btn-chosen" >${p}</div></c:if>
					<c:if test="${p!=page.pageNo}"><div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${p});" ${mouseover} ${ mouseout}>${p}</div></c:if>
					</c:forEach>
					<c:if test="${page.pageNo<page.totalPages-PC/2}">&nbsp;……&nbsp;</c:if>
					<div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${page.totalPages});" ${mouseover} ${ mouseout}>${page.totalPages}</div>
				</c:otherwise>
			</c:choose>
			
		</c:when>
		<c:otherwise>
			<c:forEach var="p" begin="${begin}" end="${end}">
			<c:if test="${p==page.pageNo}"><div id="pageBtn" class="btn btn-fillet btn-chosen" >${p}</div></c:if>
			<c:if test="${p!=page.pageNo}"><div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${p});" ${mouseover} ${ mouseout}>${p}</div></c:if>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<c:if test="${page.pageNo<page.totalPages}"><div id="pageBtn" class="btn btn-fillet" onmousedown="setBtnCss(this);" onmouseup="setPage(${page.pageNo+1});" ${mouseover} ${ mouseout} > 下一页&gt;</div> </c:if>

	<c:if test="${page.totalPages > 1}">
	<input 	type="text" id="pageInput" value=${page.pageNo}
			maxlength="${page.totalPages>1000?4:page.totalPages>100?3:page.totalPages>10?2:1}"
			style="width:${page.totalPages>1000?34:page.totalPages>100?24:17}px;" class="btn inputPage" 
			onkeyup="validatePageNo(this.value)"
			onfocus="setCaretPosition(this, this.value.length)"
			/>
	<div 	id="goToPage" class="btn btn-fillet" ${mouseover} ${ mouseout}
			onmousedown="setBtnCss(this)"
			onmouseup="setPage($('#pageInput').val());"
			>GO</div>
	</c:if>
	
</body>
<script type="text/javascript">
	function setBtnCss(obj) {
		$(obj).css({ borderColor:'#909090 #e7e7e7 #e7e7e7 #909090' });
	}
	function overBtn(obj) {
		//$(obj).css({ background:'#0066ff', color:'#ffffff' });
	}
	function outBtn(obj) {
		//$(obj).css({ background:'#ffffff', color:'#0066ff' });
	}
	
	function validatePageNo(pageNo) {
		var result = true;
		if ( pageNo=="" || isNaN(pageNo) || pageNo>${page.totalPages} || pageNo<0 || (""+pageNo).indexOf(".")>-1 ) {
			var result = false;
		}
		if (!result) {
			//$("#goToPage").unbind('mousedown').removeAttr('onmousedown');
			//$("#goToPage").unbind('mouseup').removeAttr('onmouseup');
			$("#goToPage").unbind('click').removeAttr('onclick');
			$("#goToPage").unbind('mouseover').removeAttr('onmouseover');
			$("#goToPage").unbind('mouseout').removeAttr('onmouseout');
			$("#goToPage").css( {color:"#e7e7e7", borderColor:'#e7e7e7 #e7e7e7 #e7e7e7 #e7e7e7', cursor:'text'} );
		} else {
			//$("#goToPage").bind('mousedown', this, setBtnCss);
			//$("#goToPage").bind('mouseup', $('#pageInput').val(), setPage);
			$("#goToPage").bind('click', jumpTo);
			$("#goToPage").bind('mouseover', overBtn);
			$("#goToPage").bind('mouseout', outBtn);
			$("#goToPage").css( {color:"#0066ff", borderColor:'#e7e7e7 #909090 #909090 #e7e7e7', cursor:'pointer'} );
		}
	}
	function jumpTo() {
		setBtnCss(this);
		setPage($('#pageInput').val());
	}
	
	function setCaretPosition(ctrl, pos){
	    if(ctrl.setSelectionRange)
	    {
	        ctrl.focus();
	        ctrl.setSelectionRange(pos,pos);
	    }
	    else if (ctrl.createTextRange) {
	        var range = ctrl.createTextRange();
	        range.collapse(true);
	        range.moveEnd('character', pos);
	        range.moveStart('character', pos);
	        range.select();
	    }
	}
</script>
</html>
