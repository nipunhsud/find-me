package com.findme.inbound

class Question {

    static constraints = {
    }
	
	static mapping = {
		index : "userId"
	}
	
	String id
	String userId 
	String deviceId
	String query
	String latitude
	String longitude
	String timeStamp
}
