function getReplies()
{
	
	var selectedTopic = document.topicfrm.lstAllTopics.value ;
	console.log('selectedTopic = '  +selectedTopic) ;
	document.topicfrm.hdnreplyRead.value=0;
	document.getElementById('txtdiscussion').innerHTML ='';
	var jsonVar= {
			"selectedTopic":selectedTopic,
			"repliesRead":0,
	}
	populateReplies(jsonVar);
	
	

}

function populateReplies(jsonVar )
{
	var requestStr = appURL + "rdscontroller?ajxService=replyTopic";
	var reqObject = new XMLHttpRequest();   // new HttpRequest instance 
	reqObject.open("POST", requestStr,false);
	reqObject.setRequestHeader("Content-Type", "application/json");
	reqObject.send(JSON.stringify(jsonVar));
	//console.log("Resp" + reqObject.responseText);
	var totalResponse =  JSON.parse(reqObject.responseText) ;
	var propArray =  totalResponse['updatedReplies'] ;
	var ct = 0;
	for (var i in propArray) {
	var jsonResponse = propArray[i];
	var reply= jsonResponse['Reply'];
	var repliedBy= jsonResponse['RepliedBy'];
	varRepliedByUser  = repliedBy['UserId'];
	document.getElementById('txtdiscussion').innerHTML = document.getElementById('txtdiscussion').innerHTML + 
	    "<B>" + varRepliedByUser + "</B>" +":" + reply + "<br>";
		ct ++ ;
	}
	
	document.topicfrm.hdnreplyRead.value=totalResponse['totalreplies'];

}
function checkforupdates()
{
var selectedTopic = document.topicfrm.lstAllTopics.value ;
console.log('selectedTopic = '  +selectedTopic) ;
if (selectedTopic >0 ) {
var repliesRead= document.topicfrm.hdnreplyRead.value;
var jsonVar= {
			"selectedTopic":selectedTopic,
			"repliesRead":repliesRead
	}
	
	populateReplies(jsonVar);

}

repeater = setTimeout(checkforupdates, 1000);

}

function postReply()
{
	
	var selectedTopic = document.topicfrm.lstAllTopics.value ;
	console.log('selectedTopic = '  +selectedTopic) ;
	var reply = document.topicfrm.txtnewReply.value;
	var repliesRead= document.topicfrm.hdnreplyRead.value;
     console.log('repliesRead = ' + repliesRead);
	var jsonVar= {
			"selectedTopic":selectedTopic,
			"reply":reply,
			"repliesRead":repliesRead
	}
	
	populateReplies(jsonVar);
	document.topicfrm.txtnewReply.value ='';
}
