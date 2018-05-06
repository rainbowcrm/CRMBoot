function deleteFromList() {
	var selObjs = document.getElementsByName("idChkSel");
	var oneSelected = false;
	for (var i=0; i< selObjs.length;i++){
	      if(selObjs[i].checked){
	           oneSelected =  true;
	           break;
	      }
	}
	if (oneSelected == false) {
		alert('Selected atleast one recrord to delete') ;
		return false;
	}
	var conf = confirm('Are you sure to delete the selected record(s)');
	if (conf == true )
		 return true;
	else return false;
	
}

function validateforCreate() {
	return true;
}

function openmodule( moduleName)
{
document.getElementById('currentpage').value = moduleName;
document.forms[0].submit();


}
var clickedCellIndex = -1;
function createNewCustomer(id,curControl) {
	var index  = getCurrentObjectIndex(curControl);  
	console.log('index=' + index); 
	clickedCellIndex= index;
	var dialog = document.getElementById(id);  

//	document.getElementById('idFRM' +id).contentWindow.document.forms[0].submit();
	if(!dialog.showModal)
	{
		dialogPolyfill.registerDialog(dialog);
	}
	dialog.showModal();
	
 }
function logout() 
{
	document.forms[0].currentpage.value="login";
	document.forms[0].submitAction.value="logout"
	document.forms[0].submit();
}


function showPrintDialog(id,docType) {
	var dialog = document.getElementById(id);  
	document.getElementById('printFRM').contentWindow.document.getElementById('txtid').value = document.getElementById('hdnUserID').value;
	document.getElementById('printFRM').contentWindow.document.getElementById('docType').value=docType;
	if(!dialog.showModal)
	{
		dialogPolyfill.registerDialog(dialog);
	}
	dialog.showModal();
	document.getElementById('printFRM').contentWindow.document.getElementById('cust').submit();
	
 }


function acknowledgeAlert(){
	document.frmList.submitAction.value ="acknowledge" ;
	document.frmList.submit();
}

function closeCustomerCreateDialog(dialogId) {
	window.parent.document.getElementById("txtPhone").value = window.document.getElementById("txtPhone").value;
	window.parent.document.getElementById("txtEmail").value = window.document.getElementById("txtemail").value;
	window.parent.document.getElementById("txtCustomerName").value = window.document.getElementById("txtName").value + " " + window.document.getElementById("txtLName").value;
	window.parent.document.getElementById(dialogId).close();
}

function readURL(input,imgctrl) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            console.log(imgctrl);
            reader.onload = function (e) {
                document.getElementById(imgctrl).src= e.target.result;
            };

            reader.readAsDataURL(input.files[0]);
        }
    }



function  calculateAllExpenses () {
	var ct = document.getElementsByName('txtLineReqAmt').length;
	var netPrice = 0.0 ;
	for (var i = 0 ; i < ct ; i ++ ) {
		var priceElem = document.getElementsByName('txtLineReqAmt')[i].value;
		netPrice += (priceElem * 1);
	}
	document.getElementById('txtReqTotal').value = netPrice;
}

function generateNextup(program,divisionCtrl,opControl)
{
    var requestStr = appURL + "controller?ajxService=generateNextUp&program=" +program;
	var reqObject = new XMLHttpRequest();   // new HttpRequest instance
	reqObject.open("GET", requestStr,false);

	reqObject.send();
	console.log("Resp" + reqObject.responseText);
	document.getElementById(opControl).value = reqObject.responseText;

}

function refreshParentFrame()
{
    window.opener.location.reload(false);
    window.close();

}