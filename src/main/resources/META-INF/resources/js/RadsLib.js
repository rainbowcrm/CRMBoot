
function isEven(n) {
  n = Number(n);
  return n === 0 || !!(n && !(n%2));
}

function closeDialog(dialogId) {
	window.parent.document.getElementById(dialogId).close();
}

function addRow(ctrl,oddRowStyle,evenRowStyle) {
	console.log('ctrl=' + ctrl ) ;
	var row = ctrl.parentElement.parentElement
	console.log('row=' + row ) ;
	var tabl = row.parentElement ;
	console.log('tabl=' + tabl ) ;
	var rowCount = tabl.rows.length;
	console.log('evenRowStyle=' + evenRowStyle  + ":oddRowStyle="  + oddRowStyle ) ;
	var newrow = tabl.insertRow();
	if(oddRowStyle == '' && evenRowStyle  == '') {
		var lastStyle =tabl.rows[rowCount -1].className ;
		newrow.className =lastStyle 
	}else {
	if (isEven(rowCount))
		newrow.className  = oddRowStyle  ;
	else
		newrow.className  =  evenRowStyle ;
	}
	newrow.innerHTML = row.innerHTML;
     var inputs = newrow.getElementsByTagName('input');
     console.log('inputs= ' + inputs);
            for (i = 0; i < inputs.length; i++) {
                inputs[i].value = "";
            }
	if (typeof loadAjaxServices == 'function' )
		loadAjaxServices();
}

function addRowofTable(tabl) {
	console.log('tabl=' + tabl ) ;
	var newrow = tabl.insertRow();
	//newrow.innerHTML = row.innerHTML;
	if (typeof loadAjaxServices == 'function' )
		loadAjaxServices();
}

function deleteRow(ctrl) {
	console.log('ctrl=' + ctrl ) ;
	var row = ctrl.parentElement.parentElement
	console.log('row=' + row ) ;
	var tabl = row.parentElement ;
	console.log('tabl=' + tabl ) ;
	var i = row.rowIndex - 1;
	console.log("rowindex=" + i);
    tabl.deleteRow(i);
	
}

function workBooleanCheckBoxControl(checkBoxControl, hiddenBoxControlId)
{
	console.log('workBooleanCheckBoxControl');
	var currentIndex = getCurrentObjectIndex(checkBoxControl) ;
	console.log(currentIndex);
	var hiddenControl = document.getElementsByName(hiddenBoxControlId)[currentIndex];
	console.log(checkBoxControl.checked );
	if (checkBoxControl.checked == true)
    {
		hiddenControl.value = "true";
    }else
    	hiddenControl.value = "false";
	
}

function getCurrentObjectIndex(currentCtrl)  {
	var objName = currentCtrl.name;
	var elemCount = document.getElementsByName(objName).length;
	console.log("getCurrentObjectIndex:elemCount= " + elemCount  + ":objName="  + objName);
	for (var i = 0 ; i < elemCount ; i ++ ) {
		if (document.getElementsByName(objName)[i]  == 	currentCtrl)  {
			return i ;
		}
	}
	return  0 ;
}

var clickedCellIndex = -1;
function showLookupDialog(id,curControl,additionalControl) {
	var index  = getCurrentObjectIndex(curControl);  
	console.log('index=' + index); 
	clickedCellIndex= index;
	var dialog = document.getElementById(id);  

	if( additionalControl != null &&  additionalControl != '' &&  additionalControl != 'undefined' && additionalControl != 'null')
		document.getElementById('idFRM' +id).contentWindow.document.getElementById('additionalParam').value = document.getElementById(additionalControl).value;
	
	document.getElementById('idFRM' +id).contentDocument.clickedCellIndex = index;
	console.log( " lnght"  + document.getElementById('idFRM' +id).contentWindow.document.forms.length );
	document.getElementById('idFRM' +id).contentWindow.document.forms[0].submit();
	if(!dialog.showModal)
	{
		dialogPolyfill.registerDialog(dialog);
	}
	dialog.showModal();
	
 }

function closeLookupDialogWithAdditions(dialogId,parentControl, valueControl,additionalDisplayFields) { 
	 var clkCell = window.parent.clickedCellIndex;
	 
	 console.log('additionalFields overloaded=' +additionalDisplayFields );
	 console.log('type fof ' + typeof additionalDisplayFields);
	 var  selectedValue = document.getElementById(valueControl).value;
	 var  splittedValue = selectedValue.split('|');
	 var splittedControls = additionalDisplayFields.split(',');
	 if (document.getElementById(valueControl).value != '' ){ 
	if (clkCell <= 0) { 
	 window.parent.document.getElementById(parentControl).value = splittedValue[0]; 
	 for (var i =0 ; i < splittedControls.length ; i ++ ) {
	  window.parent.document.getElementById(splittedControls[i]).value = splittedValue[i+1];
	 }
	 
	 window.parent.document.getElementById(dialogId).close(); 
	  window.parent.document.getElementById(parentControl).focus(); 
	 }else { 
	 window.parent.document.getElementsByName(parentControl)[clkCell].value = splittedValue[0]; 
	 for (var i =0 ; i < splittedControls.length ; i ++ ) {
	 console.log('splittedControls[i]=' + splittedControls[i] + ':clkCell=' + clkCell); 
	 window.parent.document.getElementsByName(splittedControls[i])[clkCell].value = splittedValue[i+1];
	 }
	 window.parent.document.getElementById(dialogId).close(); 
	  window.parent.document.getElementsByName(parentControl)[clkCell].focus(); 
	 } 
	} 
	} 

function closeLookupDialog(dialogId,parentControl, valueControl) { 
	 var clkCell = window.parent.clickedCellIndex;
	 console.log('clkCell =' + clkCell + "dialogId = "+dialogId);
	 var additionalDisplayFields =  document.getElementById('additionalControls').value; 
	 console.log('closeLookupDialog - document=' +additionalDisplayFields );
	 if ( additionalDisplayFields != null && additionalDisplayFields !='' && additionalDisplayFields !='null'  && additionalDisplayFields !='undefined' ) {
		closeLookupDialogWithAdditions(dialogId,parentControl , valueControl,additionalDisplayFields);
		return;
	 }
	 if (document.getElementById(valueControl).value != '' ){ 
	if (clkCell <= 0) { 
	 window.parent.document.getElementById(parentControl).value = document.getElementById(valueControl).value; 
	 window.parent.document.getElementById(dialogId).close(); 
	  window.parent.document.getElementById(parentControl).focus(); 
	 }else { 
	 window.parent.document.getElementsByName(parentControl)[clkCell].value = document.getElementById(valueControl).value; 
	 window.parent.document.getElementById(dialogId).close(); 
	  window.parent.document.getElementsByName(parentControl)[clkCell].focus(); 
	 } 
	} 
	} 

function showLookupDialogWithAdditionalFields(id,curControl,additionalControl,additionalCtrls, additionalFields) {
	var index  = getCurrentObjectIndex(curControl);  
	console.log('additionalFields=' + additionalFields + ": id=" + id); 
	clickedCellIndex= index;
	var dialog = document.getElementById(id);  
    console.log('element by id =' + document.getElementById('idFRM' +id)) ;
    console.log('element by id contentwindow =' + document.getElementById('idFRM' +id).contentWindow) ;
	if( additionalControl != null &&  additionalControl != '' &&  additionalControl != 'undefined' && additionalControl != 'null')
		document.getElementById('idFRM' +id).contentWindow.document.getElementById('additionalParam').value = document.getElementById(additionalControl).value;
	
	if( additionalCtrls != null &&  additionalCtrls != '' &&  additionalCtrls != 'undefined' && additionalCtrls != 'null') {
		document.getElementById('idFRM' +id).contentWindow.document.getElementById('additionalControls').value = additionalCtrls;
	}
	if( additionalFields != null &&  additionalFields != '' &&  additionalFields != 'undefined' && additionalFields != 'null') {
		document.getElementById('idFRM' +id).contentWindow.document.getElementById('additionalFields').value = additionalFields;
	}
	document.getElementById('idFRM' +id).contentDocument.clickedCellIndex = index;
	document.getElementById('idFRM' +id).contentWindow.document.forms[0].submit();
	if(!dialog.showModal)
	{
		dialogPolyfill.registerDialog(dialog);
	}
	dialog.showModal();
	
 }
function populatesupplimentary (looupType,currentCtrl,dataListCtrlName,additionalDisplayFields)
 {
	var clkCell = getCurrentObjectIndex(currentCtrl);
	var val = currentCtrl.value;
	var opts = document.getElementById(dataListCtrlName).childNodes;
	for (var i = 0; i < opts.length; i++) {
		if (opts[i].value === val) {
			var selectedValue = opts[i].innerHTML;
			var splittedValue = selectedValue.split('|');
			console.log(additionalDisplayFields);
			var splittedControls = additionalDisplayFields;
			if (currentCtrl.value != '') {
				if (clkCell <= 0) {
					//currentCtrl.value = splittedValue[0];
					for (var i = 0; i < splittedControls.length; i++) {
						document.getElementById(splittedControls[i]).value = splittedValue[i + 1];
					}

				} else {
					//currentCtrl.value = splittedValue[0];
					for (var i = 0; i < splittedControls.length; i++) {
						console.log('splittedControls[i]='
								+ splittedControls[i] + ':clkCell=' + clkCell);
						document.getElementsByName(splittedControls[i])[clkCell].value = splittedValue[i + 1];
					}

				}
				break;
			}
		}

	}

}
function getLookupWithAjax(lookupType, currentCtrl,dataListCtrlName,additionalFields,additionalInputControl)
{
	var srValue = currentCtrl.value ;
	//console.log('srValue.indexOf(*)=' + srValue.indexOf('*'));
	var index  = getCurrentObjectIndex(currentCtrl);
    	console.log( "index==" + index) ;

	if(srValue.length  > 2 || srValue.indexOf('?') !=  -1 ) {
	var additionalInputVal = '';
	if (additionalInputControl != 'null' &&  additionalInputControl != '') {
		additionalInputVal = document.getElementsByName(additionalInputControl)[index].value;
		console.log('additionalInputVal=' + additionalInputVal);
	}
	currentCtrl.autocomplete ="on";

	var searchString = 	"*"+srValue+"*";
    if (srValue.indexOf('?') !=  -1 )
        searchString = "*" ;
	var requestStr = appURL + "controller?page=Lookup&returnAsJSON=true&lookupType=" + lookupType
	+ "&additionalFields=" + additionalFields +  "&additionalParam=" + additionalInputVal   + "&searchString=" + searchString ;



	var reqObject = new XMLHttpRequest();
	reqObject.open("GET",requestStr,false);
	reqObject.send();
	console.log("Resp" + reqObject.responseText);
	if (srValue.indexOf('?') !=  -1 )
	    currentCtrl.value = '';
	var elem = document.getElementsByName(dataListCtrlName)[0];
	console.log ('before' + elem.innerHTML) ;
 	elem.innerHTML='';
	 var options = '';
	var jsonResponse =  JSON.parse(reqObject.responseText) ;
	var propArray =jsonResponse['lookupValues'] ;
	var found = false; 
	for (var i in propArray) {
		  var jsonElment = propArray[i];
		  var value = jsonElment['value'];
		  var key = jsonElment['key'];
		  options += '<option  value="'+value+'" />' + key + '</option>';
		  found =true ;
	}
	if (found == false) {
		currentCtrl.autocomplete ="off";
	}
	elem.innerHTML=  options;
	console.log ('after' + elem.innerHTML) ;
	}


}

function fireAjaxRequest (service, requestCtrls, responseCtrls, currentCtrl) {
	var requestStr = appURL + "controller?ajxService=" + service;
	var index  = getCurrentObjectIndex(currentCtrl);
	console.log(requestCtrls + "index" + index) ;
	//console.log(responseCtrls) ;
    var  property ;
	for (property in  requestCtrls) {
		    var ctrl =  requestCtrls[property];
		    var elem = '';
		    if(document.getElementsByName(ctrl).length == 1)
				elem = document.getElementsByName(ctrl)[0] ;
			else if (document.getElementsByName(ctrl).length > index)
			    elem = document.getElementsByName(ctrl)[index];
		    var value = elem.value; 
			requestStr = requestStr + "&" + property +"=" + value  ;
	}
	var reqObject = new XMLHttpRequest();
	reqObject.open("GET",requestStr,false);
	reqObject.send();
	console.log("Resp" + reqObject.responseText);
	for (property in responseCtrls) {
				var ctrl =  responseCtrls[property];
				var elem = document.getElementsByName(ctrl)[index];
				var reqCtrl = requestCtrls[property] ;
				//console.log ('reqCtrl ' + reqCtrl + 'prop' + ctrl );
				if (typeof(reqCtrl) != "undefined")
					elem.value = '';
	}
	var jsonResponse =  JSON.parse(reqObject.responseText) ;
	for (property in responseCtrls) {
		var ctrl =  responseCtrls[property];
		var elem = '';
	    if(document.getElementsByName(ctrl).length == 1)
			elem = document.getElementsByName(ctrl)[0] ;
		else if (document.getElementsByName(ctrl).length > index)
		    elem = document.getElementsByName(ctrl)[index];
		
		//console.log('elem = ' + elem  + 'ctrl =' + ctrl + 'prop =' + property) ;
		//console.log('elem type= ' + (jsonResponse[property] instanceof Array)) ;
		if (typeof(elem) == "undefined") continue ;
		elem.value = '';
//		console.log(elem.nodeName);
		if (elem.type =='select-one'  && (jsonResponse[property] instanceof Array) ){
			elem.options.length = 0;
			var propArray =jsonResponse[property] ;
			for (var i in propArray) {
				  var selectElem = propArray[i]; 
				  var text = selectElem.text;
				  var value = selectElem.value;
				  console.log (text + ":" + value + ":" + i);
				  var option = document.createElement("option");
				  option.text = text;
				  option.value=value;
				  elem.add(option);
			}
			
		}else {
		//	console.log('property=' + property) ;
			var valprop = jsonResponse[property]  ;
			//console.log('valprop=' + valprop) ;
			if(valprop != null && valprop !=  "undefined") {
				elem.value = valprop;
				if(elem.nodeName == 'SPAN' )
					elem.innerHTML= valprop;
			}
			
		}
	}

}

function refreshIFrameSrc(iframeId, iframSrc)
{
    document.getElementById(iframeId).src = iframSrc;
    event.stopPropagation();
}
function dummy() {

alert('h') ;
}
function registerEvent(ctrl, service,requestCtrls, responseCtrls) {
	var ct = document.getElementsByName(ctrl).length;
	console.log("ct=" + ct);
	for (var i = 0 ; i < ct ; i ++ ) {
		var elem = document.getElementsByName(ctrl)[i];
		console.log("i=" + i +  "Addding event listener"  + elem.name); 
		elem.addEventListener("blur", function(){  fireAjaxRequest (service, requestCtrls, responseCtrls , this) });
	}
}

function pushError(divCtrlId,newMsg) {
	var divCtrl = document.getElementById(divCtrlId);
	var existMsg = divCtrl.innerHTML ;
	divCtrl.innerHTML =   newMsg   + existMsg ;
}

function addError(divCtrlId, errorclass, errorMsg) {
	
		var divCtrl = document.getElementById(divCtrlId);
		var existMsg = divCtrl.innerHTML ;
		var newMsg = "<span class ='" + errorclass + "'>"  + errorMsg +"</span><br>";
		divCtrl.innerHTML =   newMsg   + existMsg ;
		
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function submitwithSort(sort) {
	console.log ('sorting with ' +  sort) ;
	document.getElementById('rds_sortfield').value =sort;
	if (document.getElementById('rds_sortdirection').value  ==''  || document.getElementById('rds_sortdirection').value == 'DESC')
		document.getElementById('rds_sortdirection').value ='ASC';
	else
		document.getElementById('rds_sortdirection').value ='DESC';
	document.forms[0].submit();
}