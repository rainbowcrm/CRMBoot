
function calculateAll() {
	var ct = document.getElementsByName('txtPrice').length;
	var netPrice = 0 ;
	for (var i = 0 ; i < ct ; i ++ ) {
		netPrice+= calculateLineTotal(i);
	}
	var totDiscElem = document.getElementById('txtDiscount');
	netPrice -=  totDiscElem.value;
	
	var taxPercent = document.getElementById('txtTaxPerc').value;
	var taxAmt = netPrice * taxPercent / 100;
	document.getElementById('txtTaxAmt').value = taxAmt;
	netPrice+= taxAmt ;
	document.getElementById('txtTotalAmt').value = netPrice;
}



function calculateLineTotal(index) {
	var ct = document.getElementsByName('txtPrice').length;
	var netPrice = 0 ;
	if (index < ct ) {
		var priceElem = document.getElementsByName('txtPrice')[index];
		var qtyElem = document.getElementsByName('txtQty')[index];
		var discElem = document.getElementsByName('txtlineDisc')[index];
		var totalPrice =( priceElem.value * qtyElem.value ) - discElem.value ;
		netPrice += totalPrice;
		document.getElementsByName('txtlineTotal')[index].value  = totalPrice;
	}
	return netPrice;

	
}



