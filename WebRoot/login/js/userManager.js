/**
 * 根据ID获取元素
 * @param {Object} id
 */
var gain = function(id){
    return document.getElementById(id);
}
/**
 * 根据ID获取元素值
 * @param {Object} id
 */
var gainValue = function(id){
    return gain(id).value;
}
function viewControll(param){
//    window.alert(param);
    if ("cancle" == param) {
        clearForm();
        gain("cancle").style.display = "none";
        gain("ready").style.display = "";
        gain("sure").style.display = "none";
        gain("userTab").style.display = "none";
        gain("sc").style.display = "none";
        gain("name").readOnly = false;
        gain("name").style.backgroundColor = "#ffffff";
    }
    else 
        if ("ready" == param) {
            gain("cancle").style.display = "";
            gain("ready").style.display = "none";
            gain("sure").style.display = "";
            gain("userTab").style.display = "";
            gain("name").focus();
            gain("sc").style.display = "none";
        }
}

function paging(pageNo, rootPath){
    window.location.href = "manager/userManagerAction!view.action?pageNo=" + pageNo +
    "&pageSize=" +
    gainValue("pSize") +
    "&time=" +
    new Date();
}

/**
 * 取消所有选中
 */
function clearChecked(){
    var allMenu = document.body.getElementsByTagName("INPUT");
    for (var j = 0; j < allMenu.length; j++) {
        if (allMenu[j].type == "checkbox") {
            allMenu[j].checked = false;
        }
    }
    gain("userscope1").checked=true;
	gain("userscope2").checked=false;
}

function getMenus(){
    var menus = "";
    var allMenu = document.body.getElementsByTagName("INPUT");
    for (var j = 0; j < allMenu.length; j++) {
        if (allMenu[j].type == "checkbox") {
            if (allMenu[j].checked == true) {
                menus += allMenu[j].value + ",";
            }
        }
    }
    return menus.substr(0, menus.length - 1);
}

function modify(id, username, accountname, menus,password,userscope,projectId,usertype){
    alert("modify");
	clearChecked();
    gain("userId").value = id;
    gain("name").value = username;
    gain("name").readOnly = true;
    gain("name").style.backgroundColor = "#ffff80";
    gain("nickName").value=accountname;
	gain("pwdHidden").value=password;
	gain("projectIds").value=projectId;
	
	if(null!=gain("userscope"+userscope) && "undefined"!=gain("userscope"+userscope)){
			gain("userscope"+userscope).checked=true;
	}
	if(null!=gain("usertype"+usertype) && "undefined"!=gain("usertype"+usertype)){
			gain("usertype"+usertype).checked=true;
	}
	
    if ("" != menus) {
        var ms = menus.split(",");
        var allMenu = document.body.getElementsByTagName("INPUT");
        for (var i = 0; i < ms.length; i++) {
            for (var j = 0; j < allMenu.length; j++) {
                // alert(allMenu[i].type);
                // 所有菜单权限
                if (allMenu[j].type == "checkbox") {
                    if (ms[i] == allMenu[j].value) {
                        // 选中已有权限
                        
                        allMenu[j].checked = true;
                    }
                    allMenu[j].checked = true;
                    alert("显示菜单");
                }
            }
            
        }
    }
    gain("cancle").style.display = "";
    gain("ready").style.display = "none";
    gain("sure").style.display = "none";
    gain("userTab").style.display = "";
    gain("sc").style.display = "";
    gain("password").focus();
    gain("password2").select();
}

/**
 *
 *
 * <div id="parent_27"> <lable for="parent_27"><input id="parent_27"
 * type="checkbox" name="27" value="27"/>产品管理</lable> &nbsp;&nbsp;
 *
 * <lable for="child_28"><input id="child_28" type="checkbox" name="27"
 * value=""28/>分类管理</lable>
 *
 * <lable for="child_29"><input id="child_29" type="checkbox" name="27"
 * value=""29/>添加产品</lable>
 *
 * <lable for="child_30"><input id="child_30" type="checkbox" name="27"
 * value=""30/>应用列表</lable>
 *
 * <lable for="child_31"><input id="child_31" type="checkbox" name="27"
 * value=""31/>专题推荐</lable>
 *
 * <lable for="child_32"><input id="child_32" type="checkbox" name="27"
 * value=""32/>产品目录</lable>
 *
 * </div> 点击父菜单，子菜单全部选中
 *
 * @param {}
 *            pid 父菜单ID
 */
var click = 0;
function clickParentMenu(pid){
    var menus = document.getElementsByName("parent_"+pid);
    if (click == 0) {
        for (i = 0; i < menus.length; i++) {
            if (i == 0) {
                head = menus[i];
            }
            menus[i].checked = true;
        }
        click++;
    }
    else {
        for (i = 0; i < menus.length; i++) {
            if (i == 0) {
                head = menus[i];
            }
            menus[i].checked = false;
        }
        click--;
    }
}
/**
 * 点击子菜单，父菜单也要选中
 *
 * @param {}
 *            child 子菜单ID
 * @param {}
 *            pid 父菜单ID
 */
function clickChildMenu(child, pid){

    var child = document.getElementById("child_" + child);
    var parent = document.getElementById("parent_" + pid);
    var menus = document.getElementsByName(pid);
    for (i = 1; i < menus.length; i++) {
        if (menus[i].checked) {
            parent.checked = true;
            break;
        }
        parent.checked = false;
    }
}

function clearForm(){
    gain("name").value = "";
    gain("password").value = "";
    gain("password2").value = "";
    clearChecked();
}

function del(id, rootPath){

    var msg = "您确定要删除该用户吗？";
    if (confirm(msg) == true) {
        window.location.href = "manager/userManagerAction!delete.action?id=" +
        id +
        "&version=" +
        Math.random();
    }
    
}

/**
 * 添加用户
 */
function validateForm(){
    if ("" == gainValue("username")) {
        alert("用户账号不能为空！");
        gain("username").focus();
        return;
    }
    // 添加用户 用户名称不能重复
    if (gainValue("userId") == 0) {
		checkUserName(rootPath, gainValue("username"));
	}else {
		if ("" == gainValue("userpassword") && "" == gainValue("upwd")) {
			gain("formMain").action ="manager/userManagerAction!saveOrUpdate.action" + "?pwd=" + gainValue("pwdHidden");
		}else {
			if ("" == gainValue("upwd")) {
				alert("密码不能为空！");
				gain("upwd").focus();
				return;
			}
			if ("" == gainValue("userpassword")) {
				alert("请再次输入密码！！");
				gain("userpassword").focus();
				return;
			}
			if (gainValue("upwd") != gainValue("userpassword")) {
				alert("两次密码输入不一致，请重新输入!");
				gain("userpassword").value = "";
				gain("upwd").value = "";
				gain("upwd").focus();
				return;
			}
			//填写了新密码
			gain("pwdHidden").value = gainValue("userpassword");
		}
		//保存修改
		gain("usermenus").value = getMenus();
		//提交表单
		
		gain("formMain").submit();
	}
}

function changePageSize(pageNo, rootPath){
    var pageSize = gain("pageSize").value;
	if(""==pageSize){
		pageSize=gainValue("pSize");
	}
    if ("" == pageSize) {
        gain("pageSize").focus();
        alert("每页显示条目数量不能为空！");
        return false;
    }
    var re = /^[0-9]+.?[0-9]*$/;
    if (re.test(pageSize) == false) {
        gain("pageSize").value = "";
        gain("pageSize").focus();
        alert("每页显示条目数量必须为整数！");
        return false;
    }
    else {
        var pageForm = gain("pageForm");
        var action = rootPath + "/manager/userManagerAction!view.action?pageNo=" + pageNo + "&pageSize=" + pageSize;
        pageForm.action = action;
        pageForm.method = "POST";
        pageForm.submit();
        
    }
}

function getuserxhr(){
    var xmlRequest;
    if (window.ActiveXObject) 
        xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
    else 
        if (window.XMLHttpRequest) 
            xmlRequest = new XMLHttpRequest();
    return xmlRequest;
}

var userxhr;
function checkUserName(rootPath, userName){
    userxhr = getuserxhr()
    var url = "permission/userManagerAction!checkDuplicateUserName.action?userName=" + escape(escape(userName)) + "&version=" + Math.random();
    if (userxhr == undefined) {
        alert("目前此操作只支持Trident(IE)、Gecko(FireFox)及WebKit(Chrome)内核的浏览器");
        return;
    }
    userxhr.onreadystatechange = function(){
        if (userxhr.readyState == 4 && userxhr.status == 200) {
            var isDuplicate = userxhr.responseText;
            if ("Y" == isDuplicate) {
                alert("该用户已经存在！");
                gain("username").value = "";
                gain("username").focus();
                
                return;
            }
            else {
                if ("" == gainValue("upwd")) {
                    alert("密码不能为空！");
                    gain("upwd").focus();
                    return;
                }
                if ("" == gainValue("userpassword")) {
                    alert("请再次输入密码！！");
                    gain("userpassword").focus();
                    return;
                }
                if (gainValue("upwd") != gainValue("userpassword")) {
                    alert("两次密码输入不一致，请重新输入!");
                    gain("userpassword").value = "";
                    gain("upwd").value = "";
                    gain("upwd").focus();
                    return;
                    
                }
                //保存修改
                gain("usermenus").value = getMenus();
                //提交表单
                gain("formMain").submit();
            }
        }
    };
    userxhr.open("post", url, true);
    userxhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    userxhr.send();
}
function modifyCurrentUserPwd(rootPath,oldPwdValue,id){
	if ("" == gainValue("oldPwd")) {
		alert("请输入原密码！");
		gain("oldPwd").focus();
		return;
	}
	else {
		var md5Old=hex_md5(gain("oldPwd").value);
		if(md5Old.toLowerCase()==oldPwdValue.toLowerCase()){
			if ("" == gainValue("pwd")) {
                    alert("密码不能为空！");
                    gain("pwd").focus();
                    return;
                }
                if ("" == gainValue("pwd2")) {
                    alert("请再次输入密码！！");
                    gain("pwd2").focus();
                    return;
                }
                if (gainValue("pwd") != gainValue("pwd2")) {
                    alert("两次密码输入不一致，请重新输入!");
                    gain("pwd2").value = "";
                    gain("pwd").value = "";
                    gain("pwd").focus();
                    return;
                }
			window.location.href="manager/modifyPassword!modifyPassword.action?id="+id+"&pwd="+gainValue('pwd2');
		}else{
			alert("旧密码输入不正确，请重新输入！");
			gain("oldPwd").value="";
			gain("oldPwd").focus();
		}
	}
}
