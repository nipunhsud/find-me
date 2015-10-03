package com.findme.inbound

import groovy.transform.ToString

@ToString(includeNames=true, includeFields =true, excludes="encryptionService, encryptedPassword, encryptedEmail, encryptedUsername,encryptedUserId,encryptedDeviceId,encryptedCurrentLongitude,encryptedCurrentLatitude")
class User implements Serializable{
	
	def encryptionService

    static constraints = {
		encryptedPassword(nullable:true, blank: true)
		encryptedEmail(nullable:true, blank: true)
		encryptedUsername(nullable:true, blank: true)
		 encryptedUserId(nullable:true, blank: true)
		 encryptedDeviceId(nullable:true, blank: true)
		 encryptedCurrentLongitude(nullable:true, blank: true)
		 encryptedCurrentLatitude(nullable:true, blank: true)
    }
	
	String id
	transient String username
	String currentLongitude
	String currentLatitude
	String userId
	String deviceId
	transient String password
	transient String email
	String encryptedPassword
	String encryptedEmail
	String encryptedUsername
	String encryptedUserId
	String encryptedDeviceId
	String encryptedCurrentLongitude
	String encryptedCurrentLatitude
	
	def beforeInsert() {
		if(password != null && password != ""){
			this.encryptedPassword = encryptionService.encrypt(password)
		}
		if(email != "" && email != null){
			this.encryptedEmail =encryptionService.encrypt(email)
		}
		if(username != "" && username != null){
			this.encryptedUsername =  encryptionService.encrypt(username)
		}
		if(userId != "" && userId != null){
			this.encryptedUserId =  encryptionService.encrypt(userId)
		}
		if(deviceId != "" && deviceId != null){
			this.encryptedDeviceId =  encryptionService.encrypt(deviceId)
		}
		if(currentLongitude != "" && currentLongitude != null){
			this.encryptedCurrentLongitude =  encryptionService.encrypt(currentLongitude)
		}
		if(currentLatitude != "" && currentLatitude != null){
			this.encryptedCurrentLatitude =  encryptionService.encrypt(currentLatitude)
		}
	
		
	 }
	
	def beforeUpdate(){
		if(password != null && password != ""){
			this.encryptedPassword = encryptionService.encrypt(password)
		}
		if(email != "" && email != null){
			this.encryptedEmail =encryptionService.encrypt(email)
		}
		if(username != "" && username != null){
			this.encryptedUsername =  encryptionService.encrypt(username)
		}
	}
	
	//static transients = [ "email", "password", "username" ]
	/*static mapping = {
		password column: '`password`',
		email column: '`email`'
	 }*/
	static mapping = {
		//id generator: 'assigned'
		index : "userId"
		//userId type: GormEncryptedStringType
		//password type: GormEncryptedStringType
	}
	
	public setPassword(password){
		this.password = password
	}
	
	public setEmail(email){
		this.email = email
	}
	
	public setUserId(userId){
		this.userId= userId
	}
	public setDeviceId(deviceId){
		this.deviceId= deviceId
	}
	public setCurrentLongitude(currentLongitude){
		this.currentLongitude= currentLongitude
	}
	public setCurrentLatitude(currentLatitude){
		this.currentLatitude= currentLatitude
	}
	public setUsername(username){
		this.username= username
	}
	public String getPassword(){
		if((password ==null || password.isEmpty()) && encryptedPassword !=null)
		password = encryptionService?.decrypt(encryptedPassword)
		return password
	}
	public String getUsername(){
		if((username ==null || username.isEmpty()) && encryptedUsername !=null)
		username = encryptionService?.decrypt(encryptedUsername)
		return username
	}
	public String getDeviceId(){
		if((deviceId ==null || deviceId.isEmpty()) && encryptedDeviceId !=null)
		deviceId = encryptionService?.decrypt(encryptedDeviceId)
		return deviceId
	}
	
	public String getUserId(){
		if((userId ==null || userId.isEmpty()) && encryptedUserId !=null)
		userId = encryptionService?.decrypt(encryptedUserId)
		return userId
	}
	public String getCurrentLatitude(){
		if((currentLatitude ==null || currentLatitude.isEmpty()) && encryptedCurrentLatitude !=null)
		currentLatitude = encryptionService?.decrypt(encryptedCurrentLatitude)
		return currentLatitude
	}
	public String getCurrentLongitude(){
		if((currentLongitude ==null || currentLongitude.isEmpty()) && encryptedCurrentLongitude !=null)
		currentLongitude = encryptionService?.decrypt(encryptedCurrentLongitude)
		return currentLongitude
	}
	public String getEmail(){
		if((email ==null || email.isEmpty()) && encryptedEmail !=null)
		email = encryptionService?.decrypt(encryptedEmail)
		return email
	}
}
