
function calculateAll() {
	var ct = document.getElementsByName('txtPrice').length;
	var netPrice = 0 ;
	for (var i = 0 ; i < ct ; i ++ ) {
		netPrice+= calculateLineTotal(i);
	}
	var additionalTarget = document.getElementById('txtAdditional');
	netPrice +=  parseFloat(additionalTarget.value);
	document.getElementById('txtTotalAmt').value = netPrice;
}



function calculateLineTotal(index) {
	var ct = document.getElementsByName('txtPrice').length;
	var netPrice = 0 ;
	if (index < ct ) {
		var priceElem = document.getElementsByName('txtPrice')[index];
		var qtyElem = document.getElementsByName('txtQty')[index];
		var totalPrice =( priceElem.value * qtyElem.value );
		netPrice += totalPrice;
		document.getElementsByName('txtlineTotal')[index].value  = totalPrice;
	}
	return netPrice;

	
}

