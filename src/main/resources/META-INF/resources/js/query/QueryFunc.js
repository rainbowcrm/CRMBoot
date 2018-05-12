
function submitWithParam(param)
{
	document.frmdd.submitAction.value=param;
	document.frmdd.submit();
	
}

function addRowQuery(ctrl,opType,oddRowStyle,evenRowStyle) {
	console.log('ctrl=' + ctrl ) ;
	var row = ctrl.parentElement.parentElement
	console.log('row=' + row ) ;
	var tabl = row.parentElement ;
	console.log('tabl=' + tabl ) ;
	var rowCount = tabl.rows.length;
	var index = getCurrentObjectIndex(ctrl);
	document.getElementsByName('txtandor')[index].value = opType;
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
	/*if (typeof loadAjaxServices == 'function' )
		loadAjaxServices();*/
}

function addRowofTable(tabl) {
	console.log('tabl=' + tabl ) ;
	var newrow = tabl.insertRow();
}

function deleteRow(Queryctrl) {
	console.log('ctrl=' + Queryctrl ) ;
	var row = Queryctrl.parentElement.parentElement;
	console.log('row=' + row ) ;
	var tabl = row.parentElement ;
	console.log('tabl=' + tabl ) ;
	var i = row.rowIndex - 1;
	console.log("rowindex=" + i);
    tabl.deleteRow(i);
	
}
