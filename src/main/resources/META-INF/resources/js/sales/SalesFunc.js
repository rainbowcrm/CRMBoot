

function calculateAll() {
	var ct = document.getElementsByName('txtPrice').length;
	var netPrice = 0 ;
	for (var i = 0 ; i < ct ; i ++ ) {
		netPrice+= calculateLineTotal(i);
	}
	var totDiscElem = document.getElementById('txtDiscount');
	netPrice -=  totDiscElem.value;
	
	
	var loyaltyDiscElement = document.getElementById('txtDiscountLoyaty');
	netPrice -=  loyaltyDiscElement.value;
	
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
		var discPerc = document.getElementsByName('txtlineDiscPerc')[index];
		if( discPerc != '' ){
			document.getElementsByName('txtlineDisc')[index].value = ( priceElem.value * qtyElem.value ) *  discPerc.value /100;
		}
		var discElem = document.getElementsByName('txtlineDisc')[index];
		var totalPrice =( priceElem.value * qtyElem.value ) - discElem.value ;
		netPrice += totalPrice;
		document.getElementsByName('txtlineTotal')[index].value  = totalPrice;
	}
	return netPrice;

	
}


function calculateAllforReturn() {
	var ct = document.getElementsByName('txtPrice').length;
	var netPrice = 0 ;
	for (var i = 0 ; i < ct ; i ++ ) {
		netPrice+= calculateLineTotalforReturn(i);
	}
	var totDiscElem = document.getElementById('txtDiscount');
	netPrice -=  totDiscElem.value;
	
	var taxPercent = document.getElementById('txtTaxPerc').value;
	var taxAmt = netPrice * taxPercent / 100;
	document.getElementById('txtTaxAmt').value = taxAmt;
	netPrice+= taxAmt ;
	document.getElementById('txtTotalAmt').value = netPrice;
}

function calculateLineTotalforReturn(index) {
	var ct = document.getElementsByName('txtPrice').length;
	var netPrice = 0 ;
	if (index < ct ) {
		var priceElem = document.getElementsByName('txtPrice')[index];
		var qtyElem = document.getElementsByName('txtQty')[index];
		var totalPrice =( priceElem.value * qtyElem.value ) ;
		netPrice += totalPrice;
		document.getElementsByName('txtlineTotal')[index].value  = totalPrice;
		console.log("total=" + totalPrice + "net=" + netPrice);
	}
	return netPrice  ;

	
}

function calculateExpenseTotal(index) {
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


