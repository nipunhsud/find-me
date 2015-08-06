package com.findme.outbound

class Answer {

    static constraints = {
    }
	
	static mapping = {
		index : "userId"
		index: "questionId"
	}
	
	String id
	String deviceId
	String userId
	String questionId
	String latitude
	String longitude
	
}
