var rootPath;
function initRootPath(Path) {
	rootPath = Path;
	document.getElementById("password").value="";
}
function identifieYourSelf(identity) {
	var userType = document.getElementsByName("loginIdentity");
	var bl;
	for(i=0;i<userType.length;i++) {
		if(identity!=userType[i] && userType[i].checked) bl = userType[i];
	}
	for(i=0;i<userType.length;i++) {
		if(userType[i]==bl) userType[i].checked="";
	}
}
document.onkeydown=keyListener;    
function keyListener(e){    
	e = e ? e : event;   
	if(e.keyCode == 13){    
//    	checkLogin(rootPath);
    }
}  
function changeOtherPicture(rootPath) {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    alert(ua);
    if (window.ActiveXObject) {
        Sys.ie = ua.match(/msie ([\d.]+)/)[1];
    } else if (document.getBoxObjectFor) {
        Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1];
    } else if (window.MessageEvent && !document.getBoxObjectFor) {
    	if(ua.match(/chrome\/([\d.]+)/)[1]==null)alert("没有找到匹配的");
    	
        Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1];
    } else if (window.opera) {
        Sys.opera = ua.match(/opera.([\d.]+)/)[1];
    } else if (window.openDatabase) {
        Sys.safari = ua.match(/version\/([\d.]+)/)[1];
    } 
    //以下进行测试
    if(Sys.ie) document.write('IE: '+Sys.ie);
    if(Sys.firefox) document.write('Firefox: '+Sys.firefox);
    if(Sys.chrome) document.write('Chrome: '+Sys.chrome);
    if(Sys.opera) document.write('Opera: '+Sys.opera);
    if(Sys.safari) document.write('Safari: '+Sys.safari);
	var obj = document.getElementById("checkCode");
	//alert(obj.src);
	obj.src=rootPath+"/manager/imageCode.jsp#"+new Date().getTime();
	//window.open(obj.src,"","width=400;height=300");
	//alert(obj.src);
}
