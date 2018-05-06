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


function clearAll()
{
	document.getElementById('txtcode').value='';
	document.getElementById('txtBarcode').value='';
	document.getElementById('txtItem').value='';
	document.getElementById('txtColor').value='';
	document.getElementById('txtSize').value='';
	document.getElementById('txtSpecification').value='';
	document.getElementById('idIMG2').src="''";
	document.getElementById('idIMG3').src="''";
	document.getElementById('idIMG1').src="''";
	
}