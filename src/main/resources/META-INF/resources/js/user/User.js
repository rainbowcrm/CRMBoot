/**
 * 
 */

function checkPassword() {
	var pwd = document.getElementById("txtPwd").value ;
	var confPwd = document.getElementById("txtconf").value ;
	console.log('pwd=' + pwd);
	console.log('confPwd=' + confPwd);
	if (pwd != confPwd) {
		alert('Entered passwords does not match') ;
		return false;
	}else
		return true;
}

function validateforCreate() {
	//alert('validateforCreate') ;
	return checkPassword();
}
